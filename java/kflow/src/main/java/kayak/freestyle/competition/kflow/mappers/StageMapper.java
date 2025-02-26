package kayak.freestyle.competition.kflow.mappers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import kayak.freestyle.competition.kflow.dto.StageDto;
import kayak.freestyle.competition.kflow.models.Categorie;
import kayak.freestyle.competition.kflow.models.Run;
import kayak.freestyle.competition.kflow.models.Stage;
import kayak.freestyle.competition.kflow.services.CategorieService;
import kayak.freestyle.competition.kflow.services.RunService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class StageMapper implements GenericMapper<Stage, StageDto> {

    private final RunService runService;
    private final CategorieService categorieService;

    @Override
    public StageDto modelToDto(Stage m) {
        StageDto stageDto = StageDto.builder()
                .id(m.getId())
                .nbRun(m.getNbRun())
                .rules(m.getRules())
                .nbCompetitor(m.getNbCompetitor() != 0 ? m.getNbCompetitor() : 0)
                .build();
        if (m.getCategorie() != null) {
            stageDto.setCategorie(m.getCategorie());
        }
        if (m.getRuns() != null) {
            stageDto.setRuns(m.getRuns());
        }
        return stageDto;
    }

    @Override
    public Stage dtoToModel(StageDto d) {
        Stage stage = Stage.builder()
                .id(d.getId())
                .nbRun(d.getNbRun())
                .rules(d.getRules())
                .nbCompetitor(d.getNbCompetitor() != 0 ? d.getNbCompetitor() : 0)
                // .categorie(d.getCategorie() != null ? d.getCategorie() : null)
                // .runs(d.getRuns() != null ? d.getRuns() : null)
                .build();
        Categorie categorie = d.getCategorie();
        if (categorie != null) {
            long id = categorie.getId();
            if (id > 0) {
                stage.setCategorie(categorieService.findById(id));
            }
        }
        List<Run> dtoRuns = new ArrayList<>();
        List<Run> runs = d.getRuns();
        if (!runs.isEmpty()) {
            for (Run run : runs) {
                long id = run.getId();
                if (id > 0) {
                    Run findById = runService.findById(id);
                    if (findById != null) {
                        dtoRuns.add(findById);
                    }
                }
            }
            stage.setRuns(dtoRuns);
        }
        return stage;
    }

}
