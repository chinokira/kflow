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
        if (m == null) {
            return null;
        }
        return StageDto.builder()
                .id(m.getId())
                .name(m.getName())
                .nbRun(m.getNbRun())
                .rules(m.getRules())
                .categorie(m.getCategorie())
                .runs(m.getRuns())
                .build();
    }

    @Override
    public Stage dtoToModel(StageDto d) {
        if (d == null) {
            return null;
        }
        Stage stage = Stage.builder()
                .id(d.getId())
                .name(d.getName())
                .nbRun(d.getNbRun())
                .rules(d.getRules())
                .build();

        setCategorie(stage, d.getCategorie());
        setRuns(stage, d.getRuns());
        return stage;
    }

    private void setCategorie(Stage stage, Categorie categorie) {
        if (categorie != null && categorie.getId() > 0) {
            Categorie existingCategorie = categorieService.findById(categorie.getId());
            if (existingCategorie != null) {
                stage.setCategorie(existingCategorie);
                if (!existingCategorie.getStages().contains(stage)) {
                    existingCategorie.getStages().add(stage);
                }
            }
        }
    }

    private void setRuns(Stage stage, List<Run> runs) {
        if (runs != null) {
            List<Run> stageRuns = new ArrayList<>();
            for (Run run : runs) {
                if (run.getId() > 0) {
                    addExistingRun(stage, stageRuns, run);
                }
            }
            stage.setRuns(stageRuns);
        }
    }

    private void addExistingRun(Stage stage, List<Run> stageRuns, Run run) {
        Run existingRun = runService.findById(run.getId());
        if (existingRun != null) {
            stageRuns.add(existingRun);
            existingRun.setStage(stage);
        }
    }

}
