package kayak.freestyle.competition.kflow.mappers;

import org.springframework.stereotype.Component;

import kayak.freestyle.competition.kflow.dto.CategorieDto;
import kayak.freestyle.competition.kflow.models.Categorie;

@Component
public class CategorieMapper implements GenericMapper<Categorie, CategorieDto> {


    @Override
    public CategorieDto modelToDto(Categorie m) {
        CategorieDto categorieDto = CategorieDto.builder()
                .id(m.getId())
                .name(m.getName())
                .build();

        if (m.getUsers() != null) {
            categorieDto.setUsers(m.getUsers());
        }
        if (m.getCompetition() != null) {
            categorieDto.setCompetition(m.getCompetition());
        }
        if (m.getStages() != null) {
            categorieDto.setStages(m.getStages());
        }
        return categorieDto;
    }

    @Override
    public Categorie dtoToModel(CategorieDto d) {
        Categorie categorie = Categorie.builder()
                .id(d.getId())
                .name(d.getName())
                .build();
        if (d.getUsers() != null) {
            categorie.setUsers(d.getUsers());
        }
        if (d.getCompetition() != null) {
            categorie.setCompetition(d.getCompetition());
        }
        if (d.getStages() != null) {
            categorie.setStages(d.getStages());
        }
        return categorie;
    }

}
