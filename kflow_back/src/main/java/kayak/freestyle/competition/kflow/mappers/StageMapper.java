package kayak.freestyle.competition.kflow.mappers;

import kayak.freestyle.competition.kflow.dto.StageDto;
import kayak.freestyle.competition.kflow.models.Categorie;
import kayak.freestyle.competition.kflow.models.Stage;
import kayak.freestyle.competition.kflow.services.CategorieService;
import kayak.freestyle.competition.kflow.services.RunService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class StageMapper implements GenericMapper<Stage, StageDto> {

    @SuppressWarnings("unused")
    private final RunService runService;
    @SuppressWarnings("unused")
    private final CategorieService categorieService;
    @SuppressWarnings("unused")
    private final RunMapper runMapper;
    private final CategorieMapper categorieMapper;

    public StageMapper(@Lazy RunService runService, @Lazy CategorieService categorieService, @Lazy RunMapper runMapper, @Lazy CategorieMapper categorieMapper) {
        this.runService = runService;
        this.categorieService = categorieService;
        this.runMapper = runMapper;
        this.categorieMapper = categorieMapper;
    }

    @Override
    public StageDto modelToDto(Stage model) {
        if (model == null) {
            return null;
        }
        return StageDto.builder()
                .id(model.getId())
                .name(model.getName())
                .nbRun(model.getNbRun())
                .rules(model.getRules())
                .build();
    }

    @Override
    public Stage dtoToModel(StageDto dto) {
        if (dto == null) {
            return null;
        }

        Stage model = Stage.builder()
                .id(dto.getId())
                .name(dto.getName())
                .nbRun(dto.getNbRun())
                .rules(dto.getRules())
                .build();

        // Associer la catégorie si elle est fournie dans le DTO
        if (dto.getCategorie() != null && dto.getCategorie().getId() != null) {
            Categorie categorieRef = categorieMapper.dtoToModel(dto.getCategorie());
            model.setCategorie(categorieRef);
        }

        return model;
    }
}
