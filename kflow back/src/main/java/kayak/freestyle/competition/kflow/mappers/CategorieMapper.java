package kayak.freestyle.competition.kflow.mappers;

import java.util.ArrayList;
import java.util.List;

import javax.imageio.plugins.tiff.TIFFTagSet;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import kayak.freestyle.competition.kflow.dto.CategorieDto;
import kayak.freestyle.competition.kflow.models.Categorie;
import kayak.freestyle.competition.kflow.models.Competition;
import kayak.freestyle.competition.kflow.models.Stage;
import kayak.freestyle.competition.kflow.models.User;
import kayak.freestyle.competition.kflow.services.CompetitionService;
import kayak.freestyle.competition.kflow.services.StageService;
import kayak.freestyle.competition.kflow.services.UserService;

@Component
public class CategorieMapper implements GenericMapper<Categorie, CategorieDto> {

    private final CompetitionService competitionService;
    private final UserService userService;
    private final StageService stageService;

    public CategorieMapper(@Lazy CompetitionService competitionService, @Lazy UserService userService, @Lazy StageService stageService) {
        this.competitionService = competitionService;
        this.userService = userService;
        this.stageService = stageService;
    }

    @Override
    public CategorieDto modelToDto(Categorie m) {
        return CategorieDto.builder()
                .id(m.getId())
                .name(m.getName())
                .users(m.getUsers() != null ? m.getUsers() : null)
                .stages(m.getStages() != null ? m.getStages() : null)
                .competition(m.getCompetition() != null ? m.getCompetition() : null)
                .build();
    }

    @Override
    public Categorie dtoToModel(CategorieDto d) {
        Categorie categorie = Categorie.builder()
                .id(d.getId())
                .name(d.getName())
                // .users(d.getUsers() != null ? d.getUsers() : null)
                // .competition(d.getCompetition() != null ? d.getCompetition() : null)
                // .stages(d.getStages() != null ? d.getStages() : null)
                .build();

        List<User> dtoUsers = new ArrayList<>();
        List<User> users = d.getUsers();
        if (!users.isEmpty()) {
            for (User user : users) {
                long id = user.getId();
                if (id > 0) {
                    User findById = userService.findById(id);
                    if (findById != null) {
                        dtoUsers.add(findById);
                    }
                }
            }
            categorie.setUsers(dtoUsers);
        }
        List<Stage> dtoStage = new ArrayList<>();
        List<Stage> stages = d.getStages();
        if (!stages.isEmpty()) {
            for (Stage stage : stages) {
                long id = stage.getId();
                if (id > 0) {
                    Stage findById = stageService.findById(id);
                    if (findById != null) {
                        dtoStage.add(findById);
                    }
                }
            }
            categorie.setStages(dtoStage);
        }
        long competitionId = d.getCompetition().getId();
        if (competitionId > 0) {
            Competition findById = competitionService.findById(competitionId);
            categorie.setCompetition(findById == null ? null : findById);
        }
        return categorie;
    }

}
