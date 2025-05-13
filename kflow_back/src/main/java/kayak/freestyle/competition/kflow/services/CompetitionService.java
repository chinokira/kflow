package kayak.freestyle.competition.kflow.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kayak.freestyle.competition.kflow.dto.CompetitionDto;
import kayak.freestyle.competition.kflow.dto.UpdateCompetitionDto;
import kayak.freestyle.competition.kflow.dto.CategorieDto;
import kayak.freestyle.competition.kflow.mappers.CompetitionMapper;
import kayak.freestyle.competition.kflow.models.Categorie;
import kayak.freestyle.competition.kflow.models.Competition;
import kayak.freestyle.competition.kflow.models.Participant;
import kayak.freestyle.competition.kflow.models.Run;
import kayak.freestyle.competition.kflow.models.Stage;
import kayak.freestyle.competition.kflow.repositories.CompetitionRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CompetitionService extends GenericService<Competition, CompetitionDto, CompetitionRepository, CompetitionMapper> {

    private static final Logger logger = LoggerFactory.getLogger(CompetitionService.class);

    public CompetitionService(CompetitionRepository repository, CompetitionMapper mapper) {
        super(repository, mapper);
    }

    @Override
    public CompetitionDto save(CompetitionDto dto) {
        return super.save(dto);
    }

    @Transactional(readOnly = true)
    public CompetitionDto getCompetitionWithDetails(Long id) {
        Competition competition = repository.findCompetitionWithDetails(id)
                .orElseThrow(() -> new RuntimeException("Competition not found with id: " + id));
        return mapper.modelToDto(competition);
    }

    @Transactional
    public CompetitionDto updateCompetition(Long id, UpdateCompetitionDto updateDto) {
        logger.info("Updating competition with id: {}", id);
        logger.info("Received update data: {}", updateDto);
        
        Competition existingCompetition = repository.findCompetitionWithDetails(id)
                .orElseThrow(() -> new RuntimeException("Competition not found with id: " + id));

        existingCompetition.setStartDate(LocalDate.parse(updateDto.getStartDate()));
        existingCompetition.setEndDate(LocalDate.parse(updateDto.getEndDate()));
        existingCompetition.setPlace(updateDto.getPlace());
        existingCompetition.setLevel(updateDto.getLevel());

        if (updateDto.getCategories() != null) {
            Map<Long, Categorie> existingCategories = existingCompetition.getCategories().stream()
                .collect(Collectors.toMap(Categorie::getId, c -> c));

            List<Categorie> updatedCategories = new ArrayList<>();

            for (CategorieDto catDto : updateDto.getCategories()) {
                Categorie categorie;
                if (catDto.getId() != null && existingCategories.containsKey(catDto.getId())) {
                    categorie = existingCategories.get(catDto.getId());
                    categorie.setName(catDto.getName());
                } else {
                    categorie = new Categorie();
                    categorie.setName(catDto.getName());
                    categorie.setCompetition(existingCompetition);
                }
                updatedCategories.add(categorie);
            }

            List<Categorie> categoriesToRemove = existingCompetition.getCategories().stream()
                .filter(cat -> !updatedCategories.contains(cat))
                .collect(Collectors.toList());

            for (Categorie catToRemove : categoriesToRemove) {
                for (Stage stage : catToRemove.getStages()) {
                    for (Run run : stage.getRuns()) {
                        if (run.getParticipant() != null) {
                            run.getParticipant().removeRun(run);
                        }
                        run.setParticipant(null);
                        run.setStage(null);
                    }
                    stage.getRuns().clear();
                    stage.setCategorie(null);
                }
                catToRemove.getStages().clear();

                for (Participant participant : catToRemove.getParticipants()) {
                    participant.getCategories().remove(catToRemove);
                }
                catToRemove.getParticipants().clear();

                catToRemove.setCompetition(null);
            }

            existingCompetition.getCategories().clear();
            existingCompetition.getCategories().addAll(updatedCategories);
        }

        Competition savedCompetition = repository.save(existingCompetition);
        logger.info("Saved competition: {}", savedCompetition);
        
        return mapper.modelToDto(savedCompetition);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        Competition competition = repository.findCompetitionWithDetails(id)
                .orElseThrow(() -> new RuntimeException("Competition not found with id: " + id));
        
        for (Categorie categorie : competition.getCategories()) {
            for (Stage stage : categorie.getStages()) {
                for (Run run : stage.getRuns()) {
                    if (run.getParticipant() != null) {
                        run.getParticipant().removeRun(run);
                    }
                    run.setParticipant(null);
                    run.setStage(null);
                }
                stage.getRuns().clear();
                stage.setCategorie(null);
            }
            categorie.getStages().clear();
            
            for (Participant participant : categorie.getParticipants()) {
                participant.getRuns().clear();
                participant.getCategories().remove(categorie);
            }
            categorie.getParticipants().clear();
            
            categorie.setCompetition(null);
        }
        
        competition.getCategories().clear();
        
        repository.save(competition);

        repository.delete(competition);
    }
}
