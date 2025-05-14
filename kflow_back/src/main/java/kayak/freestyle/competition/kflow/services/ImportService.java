package kayak.freestyle.competition.kflow.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kayak.freestyle.competition.kflow.dto.CategorieDto;
import kayak.freestyle.competition.kflow.dto.ParticipantDto;
import kayak.freestyle.competition.kflow.dto.RunDto;
import kayak.freestyle.competition.kflow.dto.StageDto;
import kayak.freestyle.competition.kflow.dto.importDto.ImportCompetitionDto;
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

@Service
@RequiredArgsConstructor
public class ImportService {

    private final CompetitionService competitionService;
    private final CategorieService categorieService;
    private final ParticipantService participantService;
    private final StageService stageService;
    private final RunService runService;
    private final CategorieMapper categorieMapper;
    private final ParticipantMapper participantMapper;
    private final StageMapper stageMapper;
    private final RunMapper runMapper;

    @Transactional
    public Competition importCompetition(ImportCompetitionDto importDto) {
        List<String> errors = validateImport(importDto);
        if (!errors.isEmpty()) {
            throw new IllegalArgumentException("Erreurs de validation : " + String.join(", ", errors));
        }

        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
        Competition competition = Competition.builder()
                .startDate(LocalDate.parse(importDto.getStartDate(), formatter))
                .endDate(LocalDate.parse(importDto.getEndDate(), formatter))
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

    private Categorie importCategory(CategorieDto CategoryDto, Competition competition) {
        Categorie category = createAndSaveCategory(CategoryDto, competition);
        Map<String, Stage> stageMap = createAndSaveStages(CategoryDto, category);
        if (CategoryDto.getParticipants() != null) {
            createAndSaveParticipantsWithRuns(CategoryDto, category, stageMap);
        }
        return categorieService.saveModel(category);
    }

    private Categorie createAndSaveCategory(CategorieDto CategoryDto, Competition competition) {
        Categorie category = Categorie.builder()
                .name(CategoryDto.getName())
                .competition(competition)
                .stages(new ArrayList<>())
                .participants(new HashSet<>())
                .build();
        return categorieService.saveModel(category);
    }

    private Map<String, Stage> createAndSaveStages(CategorieDto CategoryDto, Categorie categoryEntity) {
        Set<String> stageNames = collectStageNames(CategoryDto);
        Map<String, Stage> stageMap = new HashMap<>();

        CategorieDto tempCategorieDtoForStageMapping = CategorieDto.builder().id(categoryEntity.getId()).name(categoryEntity.getName()).build();

        for (String stageName : stageNames) {
            if (stageName == null || stageName.trim().isEmpty()) continue;

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

    private Set<String> collectStageNames(CategorieDto CategoryDto) {
        Set<String> stageNames = new HashSet<>();
        if (CategoryDto.getParticipants() != null) {
            for (ParticipantDto participant : CategoryDto.getParticipants()) {
                if (participant.getRuns() != null) {
                    for (RunDto run : participant.getRuns()) {
                        if (run.getStageName() != null && !run.getStageName().trim().isEmpty()) {
                            stageNames.add(run.getStageName());
                        }
                    }
                }
            }
        }
        return stageNames;
    }

    private void createAndSaveParticipantsWithRuns(CategorieDto CategoryDto, Categorie categoryEntity, Map<String, Stage> stageMap) {
        for (ParticipantDto ParticipantDto : CategoryDto.getParticipants()) {
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

    private void createAndSaveRuns(ParticipantDto ParticipantDto, Participant participantEntity, Map<String, Stage> stageMap) {
        for (RunDto RunDto : ParticipantDto.getRuns()) {
            String stageName = RunDto.getStageName();
            if (stageName != null && !stageName.trim().isEmpty() && stageMap.containsKey(stageName)) {
                Stage stageEntity = stageMap.get(stageName);
                
                StageDto tempStageDto = StageDto.builder().name(stageEntity.getName()).id(stageEntity.getId()).build();
                RunDto runDto = RunDto.builder()
                    .duration(RunDto.getDuration())
                    .score(RunDto.getScore())
                    .stage(tempStageDto)
                    .build(); 

                Run run = runMapper.dtoToModel(runDto);
                run.setParticipant(participantEntity);
                run = runService.saveModel(run);
                participantEntity.addRun(run);
            }
        }
    }

    public List<String> validateImport(ImportCompetitionDto importDto) {
        List<String> errors = new ArrayList<>();
        validateDates(importDto, errors);
        validateBasicFields(importDto, errors);
        validateCategories(importDto, errors);
        return errors;
    }

    private void validateDates(ImportCompetitionDto importDto, List<String> errors) {
        if (importDto.getStartDate() == null || importDto.getStartDate().isEmpty()) {
            errors.add("La date de début est requise");
        }
        if (importDto.getEndDate() == null || importDto.getEndDate().isEmpty()) {
            errors.add("La date de fin est requise");
        }
        if (importDto.getStartDate() != null && importDto.getEndDate() != null
                && !importDto.getStartDate().isEmpty() && !importDto.getEndDate().isEmpty()) {
            try {
                if (LocalDate.parse(importDto.getStartDate()).isAfter(LocalDate.parse(importDto.getEndDate()))) {
                    errors.add("La date de début ne peut pas être postérieure à la date de fin");
                }
            } catch (Exception e) {
                errors.add("Format de date invalide pour startDate ou endDate (attendu YYYY-MM-DD)");
            }
        }
    }

    private void validateBasicFields(ImportCompetitionDto importDto, List<String> errors) {
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

    private void validateCategories(ImportCompetitionDto importDto, List<String> errors) {
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
