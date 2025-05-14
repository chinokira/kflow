package kayak.freestyle.competition.kflow.controllers;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import kayak.freestyle.competition.kflow.dto.CategorieDto;
import kayak.freestyle.competition.kflow.dto.ParticipantDto;
import kayak.freestyle.competition.kflow.dto.RunDto;
import kayak.freestyle.competition.kflow.dto.importDto.ImportCompetitionDto;
import kayak.freestyle.competition.kflow.models.Competition;
import kayak.freestyle.competition.kflow.services.ImportService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/import")
@RequiredArgsConstructor
public class ImportController {

    private static final Logger logger = LoggerFactory.getLogger(ImportController.class);
    private final ImportService importService;
    private final ObjectMapper objectMapper;

    @PostMapping("/validate")
    public ResponseEntity<List<String>> validateImport(@RequestBody String jsonBody) {
        try {
            ImportCompetitionDto importDto = objectMapper.readValue(jsonBody, ImportCompetitionDto.class);
            JsonNode rootNode = objectMapper.readTree(jsonBody);
            populateNestedLists(importDto, rootNode);

            List<String> errors = importService.validateImport(importDto);
            if (errors.isEmpty()) {
                return ResponseEntity.ok(errors);
            }
            logger.warn("Erreurs de validation métier détectées (validateImport): {}", errors);
            return ResponseEntity.badRequest().body(errors);
        } catch (com.fasterxml.jackson.core.JsonProcessingException e) {
            logger.error("Erreur de formatage ou de mapping JSON lors de la validation: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(List.of("Erreur de formatage ou de mapping JSON: " + e.getMessage()));
        } catch (Exception e) {
            logger.error("Erreur inattendue lors de la validation: {}: {}", e.getClass().getSimpleName(), e.getMessage(), e);
            return ResponseEntity.badRequest().body(List.of("Erreur inattendue lors de la validation: " + e.getClass().getSimpleName() + " - " + e.getMessage()));
        }
    }

    @PostMapping("/competition")
    public ResponseEntity<?> importCompetition(@RequestBody String jsonBody) {
        try {
            ImportCompetitionDto importDto = objectMapper.readValue(jsonBody, ImportCompetitionDto.class);
            JsonNode rootNode = objectMapper.readTree(jsonBody);
            populateNestedLists(importDto, rootNode);

            List<String> errors = importService.validateImport(importDto);
            if (!errors.isEmpty()) {
                logger.warn("Erreurs de validation métier détectées (importCompetition - pré-validation): {}", errors);
                return ResponseEntity.badRequest().body(errors);
            }

            Competition competition = importService.importCompetition(importDto);
            return ResponseEntity.ok(competition);
        } catch (com.fasterxml.jackson.core.JsonProcessingException e) {
            logger.error("Erreur de formatage ou de mapping JSON lors de la préparation de l'import: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body("Erreur de formatage ou de mapping JSON lors de la préparation de l'import: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Erreur interne du serveur lors de l'import: {}: {}", e.getClass().getSimpleName(), e.getMessage(), e);
            return ResponseEntity.status(500).body("Erreur interne du serveur lors de l'import: " + e.getClass().getSimpleName() + " - " + e.getMessage());
        }
    }

    private void populateNestedLists(ImportCompetitionDto importDto, JsonNode rootNode) throws com.fasterxml.jackson.core.JsonProcessingException {
        JsonNode categoriesNode = rootNode.path("categories");

        if (categoriesNode.isArray() && importDto.getCategories() != null) {
            for (int i = 0; i < ((ArrayNode) categoriesNode).size(); i++) {
                JsonNode categoryJson = categoriesNode.get(i);
                if (i < importDto.getCategories().size()) {
                    CategorieDto categorieDto = importDto.getCategories().get(i);

                    JsonNode participantsNode = categoryJson.path("participants");
                    if (participantsNode.isArray()) {
                        List<ParticipantDto> participantDtoList = new ArrayList<>();
                        for (JsonNode participantJson : participantsNode) {
                            ParticipantDto participantDto = objectMapper.convertValue(participantJson, ParticipantDto.class);

                            JsonNode runsNode = participantJson.path("runs");
                            if (runsNode.isArray()) {
                                List<RunDto> runDtoList = new ArrayList<>();
                                for (JsonNode runJson : runsNode) {
                                    RunDto runDto = objectMapper.convertValue(runJson, RunDto.class);
                                    runDtoList.add(runDto);
                                }
                                participantDto.setRuns(runDtoList);
                            }
                            participantDtoList.add(participantDto);
                        }
                        categorieDto.setParticipants(participantDtoList);
                    }
                }
            }
        }
    }
}
