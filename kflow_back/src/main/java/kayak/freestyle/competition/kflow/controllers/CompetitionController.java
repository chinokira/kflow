package kayak.freestyle.competition.kflow.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kayak.freestyle.competition.kflow.dto.CompetitionDto;
import kayak.freestyle.competition.kflow.services.CompetitionService;

@RestController
@RequestMapping("/competitions")
public class CompetitionController extends GenericController<CompetitionDto, CompetitionService> {

    public CompetitionController(CompetitionService service) {
        super(service);
    }

    @GetMapping("/{id}/details")
    public ResponseEntity<CompetitionDto> getCompetitionDetails(@PathVariable Long id) {
        return ResponseEntity.ok(service.getCompetitionWithDetails(id));
    }
}
