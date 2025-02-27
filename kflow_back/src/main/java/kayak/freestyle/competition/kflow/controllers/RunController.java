package kayak.freestyle.competition.kflow.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kayak.freestyle.competition.kflow.dto.RunDto;
import kayak.freestyle.competition.kflow.services.RunService;

@RestController
@RequestMapping("/run")
public class RunController extends GenericController<RunDto, RunService> {

    public RunController(RunService service) {
        super(service);
    }

}
