package kayak.freestyle.competition.kflow.mappers;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import kayak.freestyle.competition.kflow.dto.RunDto;
import kayak.freestyle.competition.kflow.dto.StageDto;
import kayak.freestyle.competition.kflow.models.Run;
import kayak.freestyle.competition.kflow.models.Stage;
import kayak.freestyle.competition.kflow.services.ParticipantService;
import kayak.freestyle.competition.kflow.services.StageService;

@Component
public class RunMapper implements GenericMapper<Run, RunDto> {

    private final StageService stageService;
    private final ParticipantService participantService;
    private final StageMapper stageMapper;
    private final ParticipantMapper participantMapper;

    public RunMapper(@Lazy StageService stageService, @Lazy ParticipantService participantService,
            @Lazy StageMapper stageMapper, @Lazy ParticipantMapper participantMapper) {
        this.stageService = stageService;
        this.participantService = participantService;
        this.stageMapper = stageMapper;
        this.participantMapper = participantMapper;
    }

    @Override
    public RunDto modelToDto(Run model) {
        if (model == null) {
            return null;
        }
        RunDto dto = RunDto.builder()
                .id(model.getId())
                .score(model.getScore())
                .duration(model.getDuration())
                .build();

        if (model.getStage() != null) {
            StageDto stageDto = stageMapper.modelToDto(model.getStage());
            dto.setStage(stageDto);
        }

        return dto;
    }

    @Override
    public Run dtoToModel(RunDto dto) {
        if (dto == null) {
            return null;
        }

        Run model = Run.builder()
                .id(dto.getId())
                .score(dto.getScore())
                .duration(dto.getDuration())
                .build();

        if (dto.getStage() != null) {
            Stage stage = stageMapper.dtoToModel(dto.getStage());
            model.setStage(stage);
        }

        return model;
    }
}
