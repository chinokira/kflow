package kayak.freestyle.competition.kflow.mappers;

import org.springframework.stereotype.Component;

import kayak.freestyle.competition.kflow.dto.CompetitionDto;
import kayak.freestyle.competition.kflow.models.Competition;

@Component
public class CompetitionMapper implements GenericMapper<Competition, CompetitionDto> {

    @Override
    public CompetitionDto modelToDto(Competition m) {
        return CompetitionDto.builder()
            .id(m.getId())
            .date(m.getDate())
            .level(m.getLevel())
            .place(m.getPlace())
            .categories(m.getCategories())
            .build();
    }

    @Override
    public Competition dtoToModel(CompetitionDto d) {
        return Competition.builder()
            .id(d.getId())
            .date(d.getDate())
            .level(d.getLevel())
            .place(d.getPlace())
            .categories(d.getCategories())
            .build();
    }
}
