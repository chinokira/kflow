package kayak.freestyle.competition.kflow.controllers;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kayak.freestyle.competition.kflow.dto.CompetitionDto;
import kayak.freestyle.competition.kflow.dto.UpdateCompetitionDto;
import kayak.freestyle.competition.kflow.services.CompetitionService;

@RestController
@RequestMapping("/competitions")
public class CompetitionController extends GenericController<CompetitionDto, CompetitionService> {

    private static final Logger logger = LoggerFactory.getLogger(CompetitionController.class);

    public CompetitionController(CompetitionService service) {
        super(service);
    }

    @GetMapping("/{id}/details")
    public ResponseEntity<CompetitionDto> getCompetitionDetails(@PathVariable Long id) {
        CompetitionDto competitionDto = service.getCompetitionWithDetails(id);
        return ResponseEntity.ok(competitionDto);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CompetitionDto> updateCompetition(@PathVariable Long id, @RequestBody UpdateCompetitionDto updateDto) {
        logger.info("Received PUT request for competition with id: {}", id);
        logger.info("Request content type: {}", MediaType.APPLICATION_JSON_VALUE);
        logger.info("Request body: {}", updateDto);
        
        try {
            CompetitionDto updatedCompetition = service.updateCompetition(id, updateDto);
            logger.info("Updated competition: {}", updatedCompetition);
            return ResponseEntity.ok(updatedCompetition);
        } catch (Exception e) {
            logger.error("Error updating competition: {}", e.getMessage(), e);
            throw e;
        }
    }
}
