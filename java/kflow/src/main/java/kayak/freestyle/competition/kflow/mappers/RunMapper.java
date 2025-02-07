package kayak.freestyle.competition.kflow.mappers;

import org.springframework.stereotype.Component;

import kayak.freestyle.competition.kflow.dto.RunDto;
import kayak.freestyle.competition.kflow.models.Run;

@Component
public class RunMapper implements GenericMapper<Run, RunDto> {

    @Override
    public RunDto modelToDto(Run m) {
        RunDto runDto = RunDto.builder()
                .id(m.getId())
                .duration(m.getDuration())
                .score(m.getScore())
                .stage(m.getStage() != null ? m.getStage() : null)
                .build();
        return runDto;
    }

    @Override
    public Run dtoToModel(RunDto d) {
        Run run = Run.builder()
                .id(d.getId())
                .duration(d.getDuration())
                .score(d.getScore())
                .stage(d.getStage() != null ? d.getStage() : null)
                .build();
        return run;
    }

}
