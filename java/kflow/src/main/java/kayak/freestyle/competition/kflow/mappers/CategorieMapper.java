package kayak.freestyle.competition.kflow.mappers;

import org.springframework.stereotype.Component;

import kayak.freestyle.competition.kflow.dto.CategorieDto;
import kayak.freestyle.competition.kflow.models.Categorie;

@Component
public class CategorieMapper implements GenericMapper<Categorie, CategorieDto>  {

    @Override
    public CategorieDto modelToDto(Categorie m) {
        return CategorieDto.builder()
            .id(m.getId())
            .name(m.getName())
            .users(m.getUsers())
            .competition(m.getCompetition())
            .stages(m.getStages())
            .build();
    }

    @Override
    public Categorie dtoToModel(CategorieDto d) {        
        return Categorie.builder()
        .id(d.getId())
        .name(d.getName())
        .users(d.getUsers())
        .competition(d.getCompetition())
        .stages(d.getStages())
        .build();
    }

}
