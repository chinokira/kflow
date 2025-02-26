package kayak.freestyle.competition.kflow.mappers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import kayak.freestyle.competition.kflow.dto.CompetitionDto;
import kayak.freestyle.competition.kflow.models.Categorie;
import kayak.freestyle.competition.kflow.models.Competition;
import kayak.freestyle.competition.kflow.services.CategorieService;

@Component
public class CompetitionMapper implements GenericMapper<Competition, CompetitionDto> {

    private final CategorieService categorieService;

    public CompetitionMapper(@Lazy CategorieService categorieService) {
        this.categorieService = categorieService;
    }

    @Override
    public CompetitionDto modelToDto(Competition m) {
        return CompetitionDto.builder()
                .id(m.getId())
                .date(m.getDate())
                .level(m.getLevel())
                .place(m.getPlace())
                .categories(m.getCategories() != null ? m.getCategories() : null)
                .build();
    }

    @Override
    public Competition dtoToModel(CompetitionDto d) {
        Competition competition = Competition.builder()
                .id(d.getId())
                .date(d.getDate())
                .level(d.getLevel())
                .place(d.getPlace())
                // .categories(d.getCategories() != null ? d.getCategories() : null)
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
            competition.setCategories(dtoCategories);
        }
        return competition;
    }
}
