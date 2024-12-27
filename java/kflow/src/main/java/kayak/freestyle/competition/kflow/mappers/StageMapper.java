package kayak.freestyle.competition.kflow.mappers;

import org.springframework.stereotype.Component;

import kayak.freestyle.competition.kflow.dto.StageDto;
import kayak.freestyle.competition.kflow.models.Stage;

@Component
public class StageMapper implements GenericMapper<Stage, StageDto>{

    @Override
    public StageDto modelToDto(Stage m) {
        return StageDto.builder()
            .id(m.getId())
            .nbRun(m.getNbRun())
            .rules(m.getRules())
            .nbCompetitor(m.getNbCompetitor())
            .categorie(m.getCategorie())
            .runs(m.getRuns())
            .build();
    }

    @Override
    public Stage dtoToModel(StageDto d) {
        return Stage.builder()
        .id(d.getId())
        .nbRun(d.getNbRun())
        .rules(d.getRules())
        .nbCompetitor(d.getNbCompetitor())
        .categorie(d.getCategorie())
        .runs(d.getRuns())
        .build();
    }

}
