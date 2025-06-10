package kayak.freestyle.competition.kflow.mappers;

import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import kayak.freestyle.competition.kflow.dto.CompetitionDto;
import kayak.freestyle.competition.kflow.models.Categorie;
import kayak.freestyle.competition.kflow.models.Competition;

@Component
public class CompetitionMapper implements GenericMapper<Competition, CompetitionDto> {

    private final CategorieMapper categorieMapper;

    public CompetitionMapper(@Lazy CategorieMapper categorieMapper) {
        this.categorieMapper = categorieMapper;
    }

    @Override
    public CompetitionDto modelToDto(Competition model) {
        if (model == null) {
            return null;
        }
        CompetitionDto dto = CompetitionDto.builder()
                .id(model.getId())
                .startDate(model.getStartDate())
                .endDate(model.getEndDate())
                .place(model.getPlace())
                .level(model.getLevel())
                .build();

        if (model.getCategories() != null) {
            dto.setCategories(model.getCategories().stream()
                    .map(categorieMapper::modelToDto)
                    .toList());
        }

        return dto;
    }

    @Override
    public Competition dtoToModel(CompetitionDto dto) {
        if (dto == null) {
            return null;
        }

        Competition model = Competition.builder()
                .id(dto.getId())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .place(dto.getPlace())
                .level(dto.getLevel())
                .build();

        if (dto.getCategories() != null) {
            Set<Categorie> categories = new HashSet<>();
            dto.getCategories().forEach(categorieDto
                    -> categories.add(categorieMapper.dtoToModel(categorieDto))
            );
            model.setCategories(categories);
        }

        return model;
    }
}
