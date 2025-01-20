package kayak.freestyle.competition.kflow.mappers;

import org.springframework.stereotype.Component;

import kayak.freestyle.competition.kflow.dto.RunDto;
import kayak.freestyle.competition.kflow.models.Run;
import kayak.freestyle.competition.kflow.services.StageService;

@Component
public class RunMapper implements GenericMapper<Run, RunDto> {

    private StageService stageService;

    @Override
    public RunDto modelToDto(Run m) {
        RunDto runDto = RunDto.builder()
                .id(m.getId())
                .duration(m.getDuration())
                .score(m.getScore())
                .build();

        if (m.getStage() != null) {
            runDto.setStageId(m.getStage().getId());
        }
        return runDto;
    }

    @Override
    public Run dtoToModel(RunDto d) {
        Run run = Run.builder()
                .id(d.getId())
                .duration(d.getDuration())
                .score(d.getScore())
                .build();

        if (d.getStageId() != null) {
            run.setStage(stageService.findById(d.getStageId()));
        }
        return run;
    }

}
