package kayak.freestyle.competition.kflow.mappers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import kayak.freestyle.competition.kflow.dto.CompetitionDto;
import kayak.freestyle.competition.kflow.models.Categorie;
import kayak.freestyle.competition.kflow.models.Competition;
import kayak.freestyle.competition.kflow.services.CategorieService;

@Component
public class CompetitionMapper implements GenericMapper<Competition, CompetitionDto> {

    private CategorieService categorieService;

    @Override
    public CompetitionDto modelToDto(Competition m) {
        CompetitionDto competitionDto = CompetitionDto.builder()
                .id(m.getId())
                .date(m.getDate())
                .level(m.getLevel())
                .place(m.getPlace())
                .build();
        if (m.getCategories() != null) {
            List<Long> list = new ArrayList<>();
            for (Categorie cat : m.getCategories()) {
                list.add(cat.getId());
            }
            competitionDto.setCategoriesId(list);
        }
        return competitionDto;
    }

    @Override
    public Competition dtoToModel(CompetitionDto d) {
        Competition competition = Competition.builder()
                .id(d.getId())
                .date(d.getDate())
                .level(d.getLevel())
                .place(d.getPlace())
                .build();

        if (d.getCategoriesId() != null) {
            List<Categorie> list = new ArrayList<>();
            for (Long catId : d.getCategoriesId()) {
                list.add(categorieService.findById(catId));
            }
            competition.setCategories(list);
        }
        return competition;
    }
}
