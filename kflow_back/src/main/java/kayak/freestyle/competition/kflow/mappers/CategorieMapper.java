package kayak.freestyle.competition.kflow.mappers;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import kayak.freestyle.competition.kflow.dto.CategorieDto;
import kayak.freestyle.competition.kflow.dto.ParticipantDto;
import kayak.freestyle.competition.kflow.models.Categorie;
import kayak.freestyle.competition.kflow.models.Participant;
import kayak.freestyle.competition.kflow.services.ParticipantService;
import kayak.freestyle.competition.kflow.services.StageService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class CategorieMapper implements GenericMapper<Categorie, CategorieDto> {

    private final ParticipantService participantService;
    private final StageService stageService;
    private final ParticipantMapper participantMapper;
    private final StageMapper stageMapper;

    public CategorieMapper(@Lazy ParticipantService participantService, @Lazy StageService stageService,
            @Lazy ParticipantMapper participantMapper, @Lazy StageMapper stageMapper) {
        this.participantService = participantService;
        this.stageService = stageService;
        this.participantMapper = participantMapper;
        this.stageMapper = stageMapper;
    }

    @Override
    public CategorieDto modelToDto(Categorie model) {
        if (model == null) {
            return null;
        }
        CategorieDto dto = CategorieDto.builder()
                .id(model.getId())
                .name(model.getName())
                .build();

        if (model.getParticipants() != null) {
            List<ParticipantDto> participantDtos = new ArrayList<>();
            model.getParticipants().forEach(participant ->
                participantDtos.add(participantMapper.modelToDto(participant))
            );
            dto.setParticipants(participantDtos);
        }

        return dto;
    }

    @Override
    public Categorie dtoToModel(CategorieDto dto) {
        if (dto == null) {
            return null;
        }

        Categorie model = Categorie.builder()
                .id(dto.getId())
                .name(dto.getName())
                .build();

        if (dto.getParticipants() != null) {
            Set<Participant> participants = new HashSet<>();
            dto.getParticipants().forEach(participantDto ->
                participants.add(participantMapper.dtoToModel(participantDto))
            );
            model.setParticipants(participants);
        }

        return model;
    }
}
