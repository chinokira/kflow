package kayak.freestyle.competition.kflow.mappers;

import org.springframework.stereotype.Component;

import kayak.freestyle.competition.kflow.dto.StageDto;
import kayak.freestyle.competition.kflow.models.Stage;

@Component
public class StageMapper implements GenericMapper<Stage, StageDto> {

    @Override
    public StageDto modelToDto(Stage m) {
        StageDto stageDto = StageDto.builder()
        .id(m.getId())
        .nbRun(m.getNbRun())
        .rules(m.getRules())
        .build();
        if(m.getNbCompetitor() != 0)
            stageDto.setNbCompetitor(m.getNbCompetitor());
        if(m.getCategorie() != null)
            stageDto.setCategorie(m.getCategorie());
        if(m.getRuns() != null)
            stageDto.setRuns(m.getRuns());
        return stageDto;
    }

    @Override
    public Stage dtoToModel(StageDto d) {
        Stage stage = Stage.builder()
                .id(d.getId())
                .nbRun(d.getNbRun())
                .rules(d.getRules())
                .build();
        if(d.getNbCompetitor() != 0)
            stage.setNbCompetitor(d.getNbCompetitor());
        if (d.getCategorie() != null) 
            stage.setCategorie(d.getCategorie());
        if (d.getRuns() != null) 
            stage.setRuns(d.getRuns());
        return stage;
    }

}
