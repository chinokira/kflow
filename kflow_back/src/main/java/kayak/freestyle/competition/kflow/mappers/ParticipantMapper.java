package kayak.freestyle.competition.kflow.mappers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import kayak.freestyle.competition.kflow.dto.ParticipantDto;
import kayak.freestyle.competition.kflow.models.Categorie;
import kayak.freestyle.competition.kflow.models.Participant;
import kayak.freestyle.competition.kflow.models.Run;
import kayak.freestyle.competition.kflow.services.CategorieService;
import kayak.freestyle.competition.kflow.services.RunService;

@Component
public class ParticipantMapper implements GenericMapper<Participant, ParticipantDto> {

    private final RunService runService;
    private final CategorieService categorieService;

    public ParticipantMapper(@Lazy RunService runService, @Lazy CategorieService categorieService) {
        this.runService = runService;
        this.categorieService = categorieService;
    }

    @Override
    public ParticipantDto modelToDto(Participant m) {
        return ParticipantDto.builder()
                .id(m.getId())
                .bibNb(m.getBibNb())
                .name(m.getName())
                .categories(m.getCategories())
                .runs(m.getRuns())
                .build();
    }

    @Override
    public Participant dtoToModel(ParticipantDto d) {
        Participant user = Participant.builder()
                .id(d.getId())
                .bibNb(d.getBibNb())
                .name(d.getName())
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
