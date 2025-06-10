package kayak.freestyle.competition.kflow.controllers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import kayak.freestyle.competition.kflow.dto.ImportCompetitionDto;
import kayak.freestyle.competition.kflow.dto.ParticipantDto;
import kayak.freestyle.competition.kflow.dto.RunDto;
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
    public ResponseEntity<Competition> importCompetition(@RequestBody ImportCompetitionDto importDto) {
        logger.info("Received import request with {} categories", importDto.getCategories().size());

        for (CategorieDto category : importDto.getCategories()) {
            int nbParticipants = (category.getParticipants() != null) ? category.getParticipants().size() : 0;
            logger.info("Category: {} has {} participants", category.getName(), nbParticipants);
            if (category.getParticipants() != null) {
                for (ParticipantDto participant : category.getParticipants()) {
                    logger.info("Participant: {} has {} runs", participant.getName(), participant.getRuns().size());

                    for (RunDto run : participant.getRuns()) {
                        logger.info("Run for stage: {}, duration: {}, score: {}",
                                run.getStageName(), run.getDuration(), run.getScore());
                    }
                }
            } else {
                logger.warn("Category {} has no participants list (null)", category.getName());
            }
        }

        Competition competition = importService.importCompetition(importDto);
        return ResponseEntity.ok(competition);
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
                                Set<RunDto> runDtoList = new HashSet<>();
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
