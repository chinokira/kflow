package kayak.freestyle.competition.kflow.mappers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import kayak.freestyle.competition.kflow.dto.CategorieDto;
import kayak.freestyle.competition.kflow.models.Categorie;
import kayak.freestyle.competition.kflow.models.Competition;
import kayak.freestyle.competition.kflow.models.Participant;
import kayak.freestyle.competition.kflow.services.CompetitionService;
import kayak.freestyle.competition.kflow.services.ParticipantService;
import kayak.freestyle.competition.kflow.services.StageService;

@Component
public class CategorieMapper implements GenericMapper<Categorie, CategorieDto> {

    private final CompetitionService competitionService;
    private final ParticipantService participantService;

    public CategorieMapper(@Lazy CompetitionService competitionService, @Lazy ParticipantService participantService, @Lazy StageService stageService) {
        this.competitionService = competitionService;
        this.participantService = participantService;
    }

    @Override
    public CategorieDto modelToDto(Categorie m) {
        return CategorieDto.builder()
                .id(m.getId())
                .name(m.getName())
                .participants(m.getParticipants() != null ? m.getParticipants() : null)
                .competition(m.getCompetition() != null ? m.getCompetition() : null)
                .build();
    }

    @Override
    public Categorie dtoToModel(CategorieDto d) {
        Categorie categorie = Categorie.builder()
                .id(d.getId())
                .name(d.getName())
                // .participants(d.getparticipants() != null ? d.getparticipants() : null)
                // .competition(d.getCompetition() != null ? d.getCompetition() : null)
                // .stages(d.getStages() != null ? d.getStages() : null)
                .build();

        List<Participant> dtoParticipants = new ArrayList<>();
        List<Participant> participants = d.getParticipants();
        if (!participants.isEmpty()) {
            for (Participant participant : participants) {
                long id = participant.getId();
                if (id > 0) {
                    Participant findById = participantService.findById(id);
                    if (findById != null) {
                        dtoParticipants.add(findById);
                    }
                }
            }
            categorie.setParticipants(dtoParticipants);
        }
        long competitionId = d.getCompetition().getId();
        if (competitionId > 0) {
            Competition findById = competitionService.findById(competitionId);
            categorie.setCompetition(findById == null ? null : findById);
        }
        return categorie;
    }

}
