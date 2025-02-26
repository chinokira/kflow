package kayak.freestyle.competition.kflow.mappers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import kayak.freestyle.competition.kflow.dto.UserDto;
import kayak.freestyle.competition.kflow.models.Categorie;
import kayak.freestyle.competition.kflow.models.Run;
import kayak.freestyle.competition.kflow.models.User;
import kayak.freestyle.competition.kflow.services.CategorieService;
import kayak.freestyle.competition.kflow.services.RunService;

@Component
public class UserMapper implements GenericMapper<User, UserDto> {

    private final RunService runService;
    private final CategorieService categorieService;

    public UserMapper(@Lazy RunService runService, @Lazy CategorieService categorieService) {
        this.runService = runService;
        this.categorieService = categorieService;
    }

    @Override
    public UserDto modelToDto(User m) {
        return UserDto.builder()
                .id(m.getId())
                .bibNb(m.getBibNb())
                .name(m.getName())
                .email(m.getEmail())
                .password(m.getPassword())
                .categories(m.getCategories())
                .runs(m.getRuns())
                .build();
    }

    @Override
    public User dtoToModel(UserDto d) {
        User user = User.builder()
                .id(d.getId())
                .bibNb(d.getBibNb())
                .name(d.getName())
                .email(d.getEmail())
                .password(d.getPassword())
                // .categories(d.getCategories())
                // .runs(d.getRuns())
                .build();

        List<Categorie> dtoCategories = new ArrayList<>();
        List<Categorie> categories = d.getCategories();
        if (!categories.isEmpty()) {
            for (Categorie cat : categories) {
                long id = cat.getId();
                if (id > 0) {
                    Categorie findById = categorieService.findById(id);
                    if (findById != null) {
                        dtoCategories.add(findById);
                    }
                }
            }
            user.setCategories(dtoCategories);
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
            user.setRuns(dtoRuns);
        }
        return user;
    }

}
