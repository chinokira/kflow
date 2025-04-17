package kayak.freestyle.competition.kflow.mappers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
        if (m == null) {
            return null;
        }
        return CategorieDto.builder()
                .id(m.getId())
                .name(m.getName())
                .participants(m.getParticipants().stream().collect(Collectors.toSet()))
                .competition(m.getCompetition())
                .stages(m.getStages())
                .build();
    }

    @Override
    public Categorie dtoToModel(CategorieDto d) {
        if (d == null) {
            return null;
        }
        Categorie categorie = Categorie.builder()
                .id(d.getId())
                .name(d.getName())
                .build();

        if (d.getParticipants() != null) {
            Set<Participant> participants = new HashSet<>();
            for (Participant participant : d.getParticipants()) {
                if (participant.getId() > 0) {
                    Participant existingParticipant = participantService.findById(participant.getId());
                    if (existingParticipant != null) {
                        participants.add(existingParticipant);
                        if (!existingParticipant.getCategories().contains(categorie)) {
                            existingParticipant.getCategories().add(categorie);
                        }
                    }
                }
            }
            categorie.setParticipants(participants);
        }

        if (d.getCompetition() != null && d.getCompetition().getId() > 0) {
            Competition competition = competitionService.findById(d.getCompetition().getId());
            if (competition != null) {
                categorie.setCompetition(competition);
                if (!competition.getCategories().contains(categorie)) {
                    competition.getCategories().add(categorie);
                }
            }
        }

        return categorie;
    }

}
