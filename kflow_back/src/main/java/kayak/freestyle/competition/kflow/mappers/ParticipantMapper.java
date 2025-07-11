package kayak.freestyle.competition.kflow.mappers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import kayak.freestyle.competition.kflow.dto.ParticipantDto;
import kayak.freestyle.competition.kflow.dto.RunDto;
import kayak.freestyle.competition.kflow.models.Categorie;
import kayak.freestyle.competition.kflow.models.Participant;
import kayak.freestyle.competition.kflow.models.Run;
import kayak.freestyle.competition.kflow.services.CategorieService;
import kayak.freestyle.competition.kflow.services.RunService;

@Component
public class ParticipantMapper implements GenericMapper<Participant, ParticipantDto> {

    private final CategorieService categorieService;
    private final RunService runService;
    private final CategorieMapper categorieMapper;
    private final RunMapper runMapper;

    public ParticipantMapper(@Lazy CategorieService categorieService, @Lazy RunService runService,
            @Lazy CategorieMapper categorieMapper, @Lazy RunMapper runMapper) {
        this.categorieService = categorieService;
        this.runService = runService;
        this.categorieMapper = categorieMapper;
        this.runMapper = runMapper;
    }

    @Override
    public ParticipantDto modelToDto(Participant model) {
        if (model == null) {
            return null;
        }
        ParticipantDto dto = ParticipantDto.builder()
                .id(model.getId())
                .name(model.getName())
                .bibNb(model.getBibNb())
                .club(model.getClub())
                .build();

        if (model.getRuns() != null) {
            List<RunDto> runDtos = new ArrayList<>();
            model.getRuns().forEach(run
                    -> runDtos.add(runMapper.modelToDto(run))
            );
            dto.setRuns(runDtos);
        }

        return dto;
    }

    @Override
    public Participant dtoToModel(ParticipantDto dto) {
        if (dto == null) {
            return null;
        }

        Participant model = Participant.builder()
                .id(dto.getId())
                .name(dto.getName())
                .bibNb(dto.getBibNb())
                .club(dto.getClub())
                .build();

        if (dto.getCategories() != null) {
            Set<Categorie> categories = new HashSet<>();
            dto.getCategories().forEach(categorieDto
                    -> categories.add(categorieMapper.dtoToModel(categorieDto))
            );
            model.setCategories(categories);
        }
        if (dto.getRuns() != null) {
            Set<Run> runs = new HashSet<>();
            dto.getRuns().forEach(runDto
                    -> runs.add(runMapper.dtoToModel(runDto))
            );
            model.setRuns(runs);
        }

        return model;
    }
}
