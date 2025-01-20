package kayak.freestyle.competition.kflow.mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import kayak.freestyle.competition.kflow.dto.StageDto;
import kayak.freestyle.competition.kflow.models.Stage;
import kayak.freestyle.competition.kflow.services.CategorieService;
import kayak.freestyle.competition.kflow.services.RunService;

@Component
public class StageMapper implements GenericMapper<Stage, StageDto> {

    private CategorieService categorieService;
    private RunService runService;

    @Override
    public StageDto modelToDto(Stage m) {
        return StageDto.builder()
                .id(m.getId())
                .nbRun(m.getNbRun())
                .rules(m.getRules())
                .nbCompetitor(m.getNbCompetitor())
                .categorieId(m.getCategorie() != null ? m.getCategorie().getId() : null)
                .runsId(m.getRuns() != null
                        ? m.getRuns().stream().map(run -> run.getId()).toArray(Long[]::new) : new Long[0])
                .build();
    }

    @Override
    public Stage dtoToModel(StageDto d) {
        Stage stage = Stage.builder()
                .id(d.getId())
                .nbRun(d.getNbRun())
                .rules(d.getRules())
                .nbCompetitor(d.getNbCompetitor())
                .build();

        if (d.getCategorieId() != null) {
            stage.setCategorie(categorieService.findById(d.getCategorieId()));
        }

        if (d.getRunsId() != null && d.getRunsId().length > 0) {
            stage.setRuns(List.of(d.getRunsId()).stream()
                    .map(runService::findById)
                    .collect(Collectors.toList()));
        }
        return stage;
    }

}
