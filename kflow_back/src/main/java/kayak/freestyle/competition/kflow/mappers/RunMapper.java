package kayak.freestyle.competition.kflow.mappers;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import kayak.freestyle.competition.kflow.dto.RunDto;
import kayak.freestyle.competition.kflow.models.Run;
import kayak.freestyle.competition.kflow.models.Stage;
import kayak.freestyle.competition.kflow.services.StageService;

@Component
public class RunMapper implements GenericMapper<Run, RunDto> {

    private final StageService stageService;

    public RunMapper(@Lazy StageService stageService) {
        this.stageService = stageService;
    }

    @Override
    public RunDto modelToDto(Run m) {
        return RunDto.builder()
                .id(m.getId())
                .duration(m.getDuration())
                .score(m.getScore())
                .stage(m.getStage() != null ? m.getStage() : null)
                .build();
    }

    @Override
    public Run dtoToModel(RunDto d) {
        Run run = Run.builder()
                .id(d.getId())
                .duration(d.getDuration())
                .score(d.getScore())
                // .stage(d.getStage() != null ? d.getStage() : null)
                .build();
        Stage stage = d.getStage();
        if (stage != null) {
            long id = stage.getId();
            if (id > 0) {
                run.setStage(stageService.findById(id));
            }
        }
        return run;
    }

}
