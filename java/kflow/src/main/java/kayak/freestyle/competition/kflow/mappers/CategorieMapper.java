package kayak.freestyle.competition.kflow.mappers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import kayak.freestyle.competition.kflow.dto.CategorieDto;
import kayak.freestyle.competition.kflow.models.Categorie;
import kayak.freestyle.competition.kflow.models.Stage;
import kayak.freestyle.competition.kflow.models.User;
import kayak.freestyle.competition.kflow.services.CompetitionService;
import kayak.freestyle.competition.kflow.services.StageService;
import kayak.freestyle.competition.kflow.services.UserService;

@Component
public class CategorieMapper implements GenericMapper<Categorie, CategorieDto> {

    private UserService userService;
    private CompetitionService competitionService;
    private StageService stageService;

    @Override
    public CategorieDto modelToDto(Categorie m) {
        CategorieDto categorieDto = CategorieDto.builder()
                .id(m.getId())
                .name(m.getName())
                .build();

        if (m.getUsers() != null) {
            categorieDto.setUsersId(m.getUsers().stream().map(user -> user.getId()).toList());
        }
        if (m.getCompetition() != null) {
            categorieDto.setCompetitionId(m.getCompetition().getId());
        }
        if (m.getStages() != null) {
            categorieDto.setStagesId(m.getStages().stream().map(stage -> stage.getId()).toList());
        }
        return categorieDto;
    }

    @Override
    public Categorie dtoToModel(CategorieDto d) {
        Categorie categorie = Categorie.builder()
                .id(d.getId())
                .name(d.getName())
                .build();
        if (d.getUsersId() != null) {
            List<User> users = new ArrayList<>();
            for (Long id : d.getUsersId()) {
                users.add(userService.findById(id));
            }
            categorie.setUsers(users);
        }
        if (d.getCompetitionId() != null) {
            categorie.setCompetition(competitionService.findById(d.getCompetitionId()));
        }
        if (d.getStagesId() != null) {
            List<Stage> stages = new ArrayList<>();
            for (Long id : d.getStagesId()) {
                stages.add(stageService.findById(id));
            }
            categorie.setStages(stages);
        }
        return categorie;
    }

}
