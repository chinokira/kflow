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
                .startDate(m.getStartDate())
                .endDate(m.getEndDate())
                .level(m.getLevel())
                .place(m.getPlace())
                .categories(m.getCategories())
                .build();
    }

    @Override
    public Competition dtoToModel(CompetitionDto d) {
        Competition competition = Competition.builder()
                .id(d.getId())
                .startDate(d.getStartDate())
                .endDate(d.getEndDate())
                .level(d.getLevel())
                .place(d.getPlace())
                .build();

        List<Categorie> dtoCategories = new ArrayList<>();
        if (d.getCategories() != null && !d.getCategories().isEmpty()) {
            for (Categorie cat : d.getCategories()) {
                if (cat != null && cat.getId() > 0) {
                    Categorie findById = categorieService.findById(cat.getId());
                    if (findById != null) {
                        dtoCategories.add(findById);
                    }
                }
            }
            competition.setCategories(dtoCategories);
        } else {
            competition.setCategories(new ArrayList<>());
        }
        return competition;
    }
}
