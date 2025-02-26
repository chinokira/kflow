package kayak.freestyle.competition.kflow.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kayak.freestyle.competition.kflow.dto.StageDto;
import kayak.freestyle.competition.kflow.services.StageService;

@RestController
@RequestMapping("/stage")
public class StageController extends GenericController<StageDto, StageService> {

    public StageController(StageService service) {
        super(service);
    }

}
