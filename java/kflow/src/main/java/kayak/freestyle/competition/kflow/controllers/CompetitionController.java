package kayak.freestyle.competition.kflow.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kayak.freestyle.competition.kflow.dto.CompetitionDto;
import kayak.freestyle.competition.kflow.services.CompetitionService;

@RestController
@RequestMapping("/competitions")
public class CompetitionController extends GenericController<CompetitionDto, CompetitionService>{

    public CompetitionController(CompetitionService service) {
        super(service);
    }
}
