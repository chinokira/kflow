package kayak.freestyle.competition.kflow.mappers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import jakarta.validation.constraints.NotBlank;
import kayak.freestyle.competition.kflow.dto.CategorieDto;
import kayak.freestyle.competition.kflow.dto.ParticipantDto;
import kayak.freestyle.competition.kflow.dto.StageDto;
import kayak.freestyle.competition.kflow.models.Categorie;
import kayak.freestyle.competition.kflow.models.Participant;
import kayak.freestyle.competition.kflow.models.Stage;
import kayak.freestyle.competition.kflow.services.ParticipantService;
import kayak.freestyle.competition.kflow.services.StageService;

@Component
public class CategorieMapper implements GenericMapper<Categorie, CategorieDto> {

    private final ParticipantService participantService;
    private final StageService stageService;

    public CategorieMapper(@Lazy ParticipantService participantService, @Lazy StageService stageService) {
        this.participantService = participantService;
        this.stageService = stageService;
    }

    @Override
    public CategorieDto modelToDto(Categorie m) {
        if (m == null) {
            return null;
        }
        return CategorieDto.builder()
                .id(m.getId())
                .name(m.getName())
                .participants(m.getParticipants().stream()
                        .map(participant -> ParticipantDto.builder()
                                .id(participant.getId())
                                .name(participant.getName())
                                .bibNb(participant.getBibNb())
                                .build())
                        .collect(Collectors.toSet()))
                .stages(m.getStages().stream()
                        .map(stage -> StageDto.builder()
                                .id(stage.getId())
                                .name(stage.getName())
                                .nbRun(stage.getNbRun())
                                .rules(stage.getRules())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }

    @Override
    public Categorie dtoToModel(@NotBlank CategorieDto d) {
        Categorie categorie = Categorie.builder()
                .id(d.getId())
                .name(d.getName())
                .build();

        if (d.getParticipants() != null) {
            Set<Participant> participants = new HashSet<>();
            d.getParticipants()
                    .forEach(participantDto -> {
                        if (participantDto.getId() > 0) {
                            Participant existingParticipant = participantService.findById(participantDto.getId());
                            if (existingParticipant != null) {
                                participants.add(existingParticipant);
                            }
                        }
                    });
            categorie.setParticipants(participants);
        }

        if (d.getStages() != null) {
            List<Stage> stages = d.getStages().stream()
                    .filter(stageDto -> stageDto.getId() > 0)
                    .map(stageDto -> stageService.findById(stageDto.getId()))
                    .filter(stage -> stage != null)
                    .collect(Collectors.toList());
            categorie.setStages(stages);
        }

        return categorie;
    }
}
