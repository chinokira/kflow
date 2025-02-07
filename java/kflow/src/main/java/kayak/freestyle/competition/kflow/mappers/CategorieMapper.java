package kayak.freestyle.competition.kflow.mappers;

import org.springframework.stereotype.Component;

import kayak.freestyle.competition.kflow.dto.CategorieDto;
import kayak.freestyle.competition.kflow.models.Categorie;

@Component
public class CategorieMapper implements GenericMapper<Categorie, CategorieDto> {

    @Override
    public CategorieDto modelToDto(Categorie m) {
        CategorieDto dto = CategorieDto.builder()
        .id(m.getId())
        .name(m.getName())
        .users(m.getUsers()!= null ? m.getUsers() : null)
        .stages(m.getStages()!= null ? m.getStages() : null)
        .competition(m.getCompetition() != null ? m.getCompetition() : null)
        .build();
        return dto;
    }

    @Override
    public Categorie dtoToModel(CategorieDto d) {
        Categorie categorie = Categorie.builder()
                .id(d.getId())
                .name(d.getName())
                .users(d.getUsers() != null ?d.getUsers() : null)
                .competition(d.getCompetition() != null ? d.getCompetition() : null)
                .stages(d.getStages() != null ?d.getStages() : null)
                .build();
        return categorie;
    }

}
