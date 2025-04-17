package kayak.freestyle.competition.kflow.mappers;

import org.springframework.stereotype.Component;

import kayak.freestyle.competition.kflow.dto.CompetitionDto;
import kayak.freestyle.competition.kflow.models.Competition;

@Component
public class CompetitionMapper implements GenericMapper<Competition, CompetitionDto> {

    private final CategorieMapper categorieMapper;

    public CompetitionMapper(CategorieMapper categorieMapper) {
        this.categorieMapper = categorieMapper;
    }

    @Override
    public CompetitionDto modelToDto(Competition model) {
        if (model == null) {
            return null;
        }
        return CompetitionDto.builder()
                .id(model.getId())
                .startDate(model.getStartDate())
                .endDate(model.getEndDate())
                .place(model.getPlace())
                .level(model.getLevel())
                .categories(model.getCategories().stream()
                        .map(categorieMapper::modelToDto)
                        .toList())
                .build();
    }

    @Override
    public Competition dtoToModel(CompetitionDto dto) {
        if (dto == null) {
            return null;
        }

        return Competition.builder()
                .id(dto.getId())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .place(dto.getPlace())
                .level(dto.getLevel())
                .categories(dto.getCategories().stream()
                        .map(categorieMapper::dtoToModel)
                        .toList())
                .build();
    }
}
