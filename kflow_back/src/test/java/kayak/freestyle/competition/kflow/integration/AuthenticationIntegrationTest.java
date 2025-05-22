package kayak.freestyle.competition.kflow.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;

import kayak.freestyle.competition.kflow.controllers.AuthenticationController.GrantType;
import kayak.freestyle.competition.kflow.controllers.AuthenticationController.JwtRequest;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AuthenticationIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testAuthenticationFlow() throws Exception {
        // 1. Test de connexion avec des identifiants valides
        JwtRequest loginRequest = new JwtRequest();
        loginRequest.setUsername("test@example.com");
        loginRequest.setPassword("password123");
        loginRequest.setGrantType(GrantType.password);

        String response = mockMvc.perform(post("/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").exists())
                .andExpect(jsonPath("$.refreshToken").exists())
                .andReturn()
                .getResponse()
                .getContentAsString();

        // Extraire le refresh token de la réponse
        String refreshToken = objectMapper.readTree(response).get("refreshToken").asText();

        // 2. Test de rafraîchissement du token
        JwtRequest refreshRequest = new JwtRequest();
        refreshRequest.setRefreshToken(refreshToken);
        refreshRequest.setGrantType(GrantType.refreshToken);

        mockMvc.perform(post("/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(refreshRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").exists())
                .andExpect(jsonPath("$.refreshToken").exists());
    }

    @Test
    public void testAuthenticationWithInvalidCredentials() throws Exception {
        JwtRequest loginRequest = new JwtRequest();
        loginRequest.setUsername("test@example.com");
        loginRequest.setPassword("wrongpassword");
        loginRequest.setGrantType(GrantType.password);

        mockMvc.perform(post("/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testAuthenticationWithInvalidGrantType() throws Exception {
        JwtRequest loginRequest = new JwtRequest();
        loginRequest.setUsername("test@example.com");
        loginRequest.setPassword("password123");
        loginRequest.setGrantType(null);

        mockMvc.perform(post("/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isBadRequest());
    }
} 