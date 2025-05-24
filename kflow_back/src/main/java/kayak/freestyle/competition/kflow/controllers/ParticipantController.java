package kayak.freestyle.competition.kflow.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;
import kayak.freestyle.competition.kflow.dto.RunDto;

import kayak.freestyle.competition.kflow.dto.ParticipantDto;
import kayak.freestyle.competition.kflow.services.ParticipantService;

@RestController
@RequestMapping("/participants")
public class ParticipantController extends GenericController<ParticipantDto, ParticipantService> {

    private final ParticipantService participantService;

    public ParticipantController(ParticipantService participantService) {
        super(participantService);
        this.participantService = participantService;
    }

    @GetMapping("/{participantId}/runs")
    public List<RunDto> getRunsForParticipant(@PathVariable Long participantId) {
        return participantService.getRunsByParticipantId(participantId);
    }
}
