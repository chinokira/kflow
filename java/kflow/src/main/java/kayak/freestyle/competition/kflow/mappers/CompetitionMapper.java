package kayak.freestyle.competition.kflow.mappers;

import org.springframework.stereotype.Component;

import kayak.freestyle.competition.kflow.dto.CompetitionDto;
import kayak.freestyle.competition.kflow.models.Competition;

@Component
public class CompetitionMapper implements GenericMapper<Competition, CompetitionDto> {

    @Override
    public CompetitionDto modelToDto(Competition m) {
        CompetitionDto competitionDto = CompetitionDto.builder()
                .id(m.getId())
                .date(m.getDate())
                .level(m.getLevel())
                .place(m.getPlace())
                .categories(m.getCategories() != null ? m.getCategories() : null)
                .build();
        return competitionDto;
    }

    @Override
    public Competition dtoToModel(CompetitionDto d) {
        Competition competition = Competition.builder()
                .id(d.getId())
                .date(d.getDate())
                .level(d.getLevel())
                .place(d.getPlace())
                .categories(d.getCategories() != null ? d.getCategories() : null)
                .build();
        return competition;
    }
}
