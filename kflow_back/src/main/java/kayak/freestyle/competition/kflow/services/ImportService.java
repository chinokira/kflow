package kayak.freestyle.competition.kflow.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kayak.freestyle.competition.kflow.dto.CategorieDto;
import kayak.freestyle.competition.kflow.dto.CompetitionDto;
import kayak.freestyle.competition.kflow.dto.ParticipantDto;
import kayak.freestyle.competition.kflow.dto.RunDto;
import kayak.freestyle.competition.kflow.dto.StageDto;
import kayak.freestyle.competition.kflow.mappers.CategorieMapper;
import kayak.freestyle.competition.kflow.mappers.ParticipantMapper;
import kayak.freestyle.competition.kflow.mappers.RunMapper;
import kayak.freestyle.competition.kflow.mappers.StageMapper;
import kayak.freestyle.competition.kflow.models.Categorie;
import kayak.freestyle.competition.kflow.models.Competition;
import kayak.freestyle.competition.kflow.models.Participant;
import kayak.freestyle.competition.kflow.models.Run;
import kayak.freestyle.competition.kflow.models.Stage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ImportService {

    private static final Logger logger = LoggerFactory.getLogger(ImportService.class);

    private final CompetitionService competitionService;
    private final CategorieService categorieService;
    private final ParticipantService participantService;
    private final StageService stageService;
    private final RunService runService;
    @SuppressWarnings("unused")
    private final CategorieMapper categorieMapper;
    private final ParticipantMapper participantMapper;
    private final StageMapper stageMapper;
    private final RunMapper runMapper;

    @Transactional
    public Competition importCompetition(CompetitionDto importDto) {
        List<String> errors = validateImport(importDto);
        if (!errors.isEmpty()) {
            throw new IllegalArgumentException("Erreurs de validation : " + String.join(", ", errors));
        }
        Competition competition = Competition.builder()
                .startDate(importDto.getStartDate())
                .endDate(importDto.getEndDate())
                .level(importDto.getLevel())
                .place(importDto.getPlace())
                .build();
        competition = competitionService.saveModel(competition);

        if (importDto.getCategories() != null) {
            for (CategorieDto CategoryDto : importDto.getCategories()) {
                Categorie category = importCategory(CategoryDto, competition);
                competition.addCategorie(category);
            }
        }
        return competition;
    }

    private Categorie importCategory(CategorieDto categoryDto, Competition competition) {
        Categorie category = createAndSaveCategory(categoryDto, competition);
        Map<String, Stage> stageMap = createAndSaveStages(categoryDto, category);
        if (categoryDto.getParticipants() != null) {
            createAndSaveParticipantsWithRuns(categoryDto, category, stageMap);
        }
        return categorieService.saveModel(category);
    }

    private Categorie createAndSaveCategory(CategorieDto categoryDto, Competition competition) {
        Categorie category = Categorie.builder()
                .name(categoryDto.getName())
                .competition(competition)
                .stages(new ArrayList<>())
                .participants(new HashSet<>())
                .build();
        return categorieService.saveModel(category);
    }

    private Map<String, Stage> createAndSaveStages(CategorieDto categoryDto, Categorie categoryEntity) {
        Set<String> stageNames = collectStageNames(categoryDto);
        Map<String, Stage> stageMap = new HashMap<>();

        CategorieDto tempCategorieDtoForStageMapping = CategorieDto.builder().id(categoryEntity.getId()).name(categoryEntity.getName()).build();

        for (String stageName : stageNames) {
            if (stageName == null || stageName.trim().isEmpty()) {
                continue;
            }

            StageDto stageDto = StageDto.builder()
                    .name(stageName)
                    .nbRun(2)
                    .rules("Standard")
                    .categorie(tempCategorieDtoForStageMapping)
                    .build();
            Stage stage = stageMapper.dtoToModel(stageDto);
            stage = stageService.saveModel(stage);
            stageMap.put(stageName, stage);
            categoryEntity.getStages().add(stage);
        }
        return stageMap;
    }

    private Set<String> collectStageNames(CategorieDto categoryDto) {
        Set<String> stageNames = new HashSet<>();
        if (categoryDto.getParticipants() != null) {
            for (ParticipantDto participant : categoryDto.getParticipants()) {
                addStageNamesFromParticipant(participant, stageNames);
            }
        }
        return stageNames;
    }

    private void addStageNamesFromParticipant(ParticipantDto participant, Set<String> stageNames) {
        if (participant.getRuns() != null) {
            for (RunDto run : participant.getRuns()) {
                if (run.getStageName() != null && !run.getStageName().trim().isEmpty()) {
                    stageNames.add(run.getStageName());
                }
            }
        }
    }

    private void createAndSaveParticipantsWithRuns(CategorieDto categoryDto, Categorie categoryEntity, Map<String, Stage> stageMap) {
        for (ParticipantDto ParticipantDto : categoryDto.getParticipants()) {
            Participant participant = createAndSaveParticipant(ParticipantDto, categoryEntity);
            if (ParticipantDto.getRuns() != null) {
                createAndSaveRuns(ParticipantDto, participant, stageMap);
            }
            participantService.saveModel(participant);
        }
    }

    private Participant createAndSaveParticipant(ParticipantDto participantDtoIncoming, Categorie categoryEntity) {
        ParticipantDto participantDtoForSave = ParticipantDto.builder()
                .name(participantDtoIncoming.getName())
                .bibNb(participantDtoIncoming.getBibNb())
                .club(participantDtoIncoming.getClub())
                .build();

        Participant participant = participantMapper.dtoToModel(participantDtoForSave);

        categoryEntity.addParticipant(participant);

        participant = participantService.saveModel(participant);

        return participant;
    }

    private void createAndSaveRuns(ParticipantDto participantDto, Participant participantEntity, Map<String, Stage> stageMap) {
        logger.info("Creating runs for participant: {}", participantEntity.getName());
        logger.info("Number of runs in DTO: {}", participantDto.getRuns().size());

        for (RunDto runDtoIncoming : participantDto.getRuns()) {
            processSingleRun(runDtoIncoming, participantEntity, stageMap);
        }

        logger.info("Final number of runs for participant {}: {}", participantEntity.getName(), participantEntity.getRuns().size());
    }

    private void processSingleRun(RunDto runDtoIncoming, Participant participantEntity, Map<String, Stage> stageMap) {
        String stageName = runDtoIncoming.getStageName();
        logger.info("Processing run for stage: {}", stageName);

        if (stageName == null || stageName.trim().isEmpty() || !stageMap.containsKey(stageName)) {
            logger.warn("Stage not found or invalid for run: {}", stageName);
            return;
        }

        Stage stageEntity = stageMap.get(stageName);
        logger.info("Found stage entity: {}", stageEntity.getName());

        StageDto tempStageDto = StageDto.builder().name(stageEntity.getName()).id(stageEntity.getId()).build();
        RunDto runDto = RunDto.builder()
                .duration(runDtoIncoming.getDuration())
                .score(runDtoIncoming.getScore())
                .stage(tempStageDto)
                .build();

        Run run = runMapper.dtoToModel(runDto);
        run.setParticipant(participantEntity);
        run = runService.saveModel(run);
        participantEntity.addRun(run);
        logger.info("Saved run with ID: {} for participant: {}", run.getId(), participantEntity.getName());
    }

    public List<String> validateImport(CompetitionDto importDto) {
        List<String> errors = new ArrayList<>();
        validateDates(importDto, errors);
        validateBasicFields(importDto, errors);
        validateCategories(importDto, errors);
        return errors;
    }

    private void validateDates(CompetitionDto importDto, List<String> errors) {
        if (importDto.getStartDate() == null) {
            errors.add("La date de début est requise");
        }
        if (importDto.getEndDate() == null) {
            errors.add("La date de fin est requise");
        }
        if (importDto.getStartDate() != null && importDto.getEndDate() != null) {
            try {
                if (importDto.getStartDate().isAfter(importDto.getEndDate())) {
                    errors.add("La date de début ne peut pas être postérieure à la date de fin");
                }
            } catch (Exception e) {
                errors.add("Format de date invalide pour startDate ou endDate (attendu YYYY-MM-DD)");
            }
        }
    }

    private void validateBasicFields(CompetitionDto importDto, List<String> errors) {
        if (importDto.getLevel() == null || importDto.getLevel().isEmpty()) {
            errors.add("Le niveau est requis");
        }
        if (importDto.getPlace() == null || importDto.getPlace().isEmpty()) {
            errors.add("Le lieu est requis");
        }
        if (importDto.getCategories() == null || importDto.getCategories().isEmpty()) {
            errors.add("Au moins une catégorie est requise");
        }
    }

    private void validateCategories(CompetitionDto importDto, List<String> errors) {
        if (importDto.getCategories() != null) {
            Set<String> categoryNames = new HashSet<>();
            for (CategorieDto category : importDto.getCategories()) {
                if (category.getName() != null && !categoryNames.add(category.getName())) {
                    errors.add("Le nom de catégorie '" + category.getName() + "' est en double");
                }
                validateCategory(category, errors);
            }
        }
    }

    private void validateCategory(CategorieDto category, List<String> errors) {
        validateCategoryBasics(category, errors);
        validateParticipants(category, errors);
    }

    private void validateCategoryBasics(CategorieDto category, List<String> errors) {
        if (category.getName() == null || category.getName().isEmpty()) {
            errors.add("Le nom de la catégorie est requis");
        }
    }

    private void validateParticipants(CategorieDto category, List<String> errors) {
        if (category.getParticipants() != null) {
            Set<Integer> bibNumbers = new HashSet<>();
            for (ParticipantDto participant : category.getParticipants()) {
                validateParticipant(participant, category.getName(), bibNumbers, errors);
            }
        }
    }

    private void validateParticipant(ParticipantDto participant, String categoryName, Set<Integer> bibNumbers, List<String> errors) {
        validateParticipantBasics(participant, categoryName, bibNumbers, errors);
        validateParticipantRuns(participant, errors);
    }

    private void validateParticipantBasics(ParticipantDto participant, String categoryName, Set<Integer> bibNumbers, List<String> errors) {
        if (participant.getName() == null || participant.getName().isEmpty()) {
            errors.add("Le nom du participant est requis dans la catégorie " + categoryName);
        }
        if (participant.getBibNb() <= 0) {
            errors.add("Le numéro de dossard doit être positif pour le participant '" + participant.getName() + "' dans la catégorie " + categoryName);
        }
        if (!bibNumbers.add(participant.getBibNb())) {
            errors.add("Le numéro de dossard " + participant.getBibNb() + " est en double dans la catégorie " + categoryName);
        }
    }

    private void validateParticipantRuns(ParticipantDto participant, List<String> errors) {
        if (participant.getRuns() != null) {
            for (RunDto run : participant.getRuns()) {
                validateRun(run, errors);
            }
        }
    }

    private void validateRun(RunDto run, List<String> errors) {
        if (run.getStageName() == null || run.getStageName().trim().isEmpty()) {
            errors.add("Le nom de l'étape (stageName) est requis pour chaque run.");
        }
    }
}
