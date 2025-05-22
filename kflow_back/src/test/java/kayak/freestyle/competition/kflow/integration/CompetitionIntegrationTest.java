package kayak.freestyle.competition.kflow.integration;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;

import kayak.freestyle.competition.kflow.controllers.AuthenticationController.GrantType;
import kayak.freestyle.competition.kflow.controllers.AuthenticationController.JwtRequest;
import kayak.freestyle.competition.kflow.dto.CompetitionDto;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CompetitionIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private String accessToken;

    @BeforeEach
    public void setup() throws Exception {
        // Authentification pour obtenir le token
        JwtRequest loginRequest = new JwtRequest();
        loginRequest.setUsername("admin@example.com");
        loginRequest.setPassword("admin123");
        loginRequest.setGrantType(GrantType.password);

        String response = mockMvc.perform(post("/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        accessToken = objectMapper.readTree(response).get("accessToken").asText();
    }

    @Test
    public void testCompetitionCRUD() throws Exception {
        // 1. Création d'une compétition
        CompetitionDto competition = CompetitionDto.builder()
                .level("Test Competition")
                .place("Test Description")
                .startDate(LocalDate.parse("2024-03-01"))
                .endDate(LocalDate.parse("2024-03-02"))
                .build();

        String response = mockMvc.perform(post("/competitions")
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(competition)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.level").value("Test Competition"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        // Extraire l'ID de la compétition créée
        Long competitionId = objectMapper.readTree(response).get("id").asLong();

        // 2. Lecture de la compétition
        mockMvc.perform(get("/competitions/" + competitionId)
                .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(competitionId))
                .andExpect(jsonPath("$.level").value("Test Competition"));

        // 3. Mise à jour de la compétition
        competition = CompetitionDto.builder()
                .id(competitionId)
                .level("Updated Competition")
                .place("Test Description")
                .startDate(LocalDate.parse("2024-03-01"))
                .endDate(LocalDate.parse("2024-03-02"))
                .build();

        mockMvc.perform(put("/competitions/" + competitionId)
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(competition)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.level").value("Updated Competition"));

        // 4. Suppression de la compétition
        mockMvc.perform(delete("/competitions/" + competitionId)
                .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk());

        // 5. Vérifier que la compétition n'existe plus
        mockMvc.perform(get("/competitions/" + competitionId)
                .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUnauthorizedAccess() throws Exception {
        // Test d'accès sans token
        mockMvc.perform(get("/competitions"))
                .andExpect(status().isUnauthorized());
    }
} 