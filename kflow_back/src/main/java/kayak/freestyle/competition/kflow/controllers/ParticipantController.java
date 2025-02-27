package kayak.freestyle.competition.kflow.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kayak.freestyle.competition.kflow.dto.ParticipantDto;
import kayak.freestyle.competition.kflow.services.ParticipantService;

@RestController
@RequestMapping("/users")
public class ParticipantController extends GenericController<ParticipantDto, ParticipantService> {

    public ParticipantController(ParticipantService userService) {
        super(userService);
    }
}
