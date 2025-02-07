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
                .nbCompetitor(m.getNbCompetitor() != 0 ? m.getNbCompetitor() : 0)
                .build();
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
                .nbCompetitor(d.getNbCompetitor() != 0 ? d.getNbCompetitor() : 0)
                .categorie(d.getCategorie() != null ? d.getCategorie() : null)
                .runs(d.getRuns() != null ? d.getRuns() : null)
                .build();
        if(d.getCategorie() != null)
            stage.setCategorie(d.getCategorie());
        if(d.getRuns() != null)
            stage.setRuns(d.getRuns());
        return stage;
    }

}
