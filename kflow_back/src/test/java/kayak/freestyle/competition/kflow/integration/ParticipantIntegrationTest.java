

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
import kayak.freestyle.competition.kflow.dto.ParticipantDto;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ParticipantIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private String accessToken;
    private Long competitionId;

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

        // Créer une compétition pour les tests
        CompetitionDto competition = CompetitionDto.builder()
                .level("Test Competition")
                .place("Test Description")
                .startDate(LocalDate.parse("2024-03-01"))
                .endDate(LocalDate.parse("2024-03-02"))
                .build();

        String competitionResponse = mockMvc.perform(post("/competitions")
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(competition)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        competitionId = objectMapper.readTree(competitionResponse).get("id").asLong();
    }

    @Test
    public void testParticipantManagement() throws Exception {
        // 1. Ajouter un participant
        ParticipantDto participant = ParticipantDto.builder()
                .name("Test Participant")
                .club("Test Club")
                .build();

        String response = mockMvc.perform(post("/competitions/" + competitionId + "/participants")
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(participant)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("Test Participant"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        Long participantId = objectMapper.readTree(response).get("id").asLong();

        // 2. Récupérer la liste des participants
        mockMvc.perform(get("/competitions/" + competitionId + "/participants")
                .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(participantId))
                .andExpect(jsonPath("$[0].name").value("Test Participant"));

        // 3. Mettre à jour un participant
        participant = ParticipantDto.builder()
                .id(participantId)
                .name("Updated Participant")
                .club("Test Club")
                .build();

        mockMvc.perform(put("/competitions/" + competitionId + "/participants/" + participantId)
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(participant)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Participant"));

        // 4. Supprimer un participant
        mockMvc.perform(delete("/competitions/" + competitionId + "/participants/" + participantId)
                .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk());

        // 5. Vérifier que le participant n'existe plus
        mockMvc.perform(get("/competitions/" + competitionId + "/participants")
                .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    public void testInvalidParticipantOperations() throws Exception {
        // Test d'ajout de participant avec données invalides
        ParticipantDto invalidParticipant = ParticipantDto.builder()
                .name("") // Nom vide
                .build();

        mockMvc.perform(post("/competitions/" + competitionId + "/participants")
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidParticipant)))
                .andExpect(status().isBadRequest());

        // Test d'accès à une compétition inexistante
        mockMvc.perform(get("/competitions/999/participants")
                .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isNotFound());
    }
} 