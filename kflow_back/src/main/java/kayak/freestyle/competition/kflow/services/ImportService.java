package kayak.freestyle.competition.kflow.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kayak.freestyle.competition.kflow.dto.ImportCompetitionDto;
import kayak.freestyle.competition.kflow.models.Categorie;
import kayak.freestyle.competition.kflow.models.Competition;
import kayak.freestyle.competition.kflow.models.Participant;
import kayak.freestyle.competition.kflow.models.Run;
import kayak.freestyle.competition.kflow.models.Stage;
import lombok.RequiredArgsConstructor;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class ImportService {

    private final CompetitionService competitionService;
    private final CategorieService categorieService;
    private final ParticipantService participantService;
    private final StageService stageService;
    private final RunService runService;

    @Transactional
    public Competition importCompetition(ImportCompetitionDto importDto) {
        // Valider l'import avant de commencer
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

        // Importer les catégories
        for (ImportCompetitionDto.ImportCategoryDto categoryDto : importDto.getCategories()) {
            Categorie category = importCategory(categoryDto, competition);
            competition.addCategorie(category);
        }

        return competition;
    }

    private Categorie importCategory(ImportCompetitionDto.ImportCategoryDto categoryDto, Competition competition) {
        Categorie category = createAndSaveCategory(categoryDto, competition);
        Map<String, Stage> stageMap = createAndSaveStages(categoryDto, category);
        createAndSaveParticipantsWithRuns(categoryDto, category, stageMap);
        return categorieService.saveModel(category);
    }

    private Categorie createAndSaveCategory(ImportCompetitionDto.ImportCategoryDto categoryDto, Competition competition) {
        Categorie category = Categorie.builder()
                .name(categoryDto.getName())
                .competition(competition)
                .stages(new ArrayList<>())
                .participants(new HashSet<>())
                .build();
        return categorieService.saveModel(category);
    }

    private Map<String, Stage> createAndSaveStages(ImportCompetitionDto.ImportCategoryDto categoryDto, Categorie category) {
        Set<String> stageNames = collectStageNames(categoryDto);
        Map<String, Stage> stageMap = new HashMap<>();

        for (String stageName : stageNames) {
            Stage stage = Stage.builder()
                    .name(stageName)
                    .nbRun(2)
                    .rules("Standard")
                    .categorie(category)
                    .runs(new ArrayList<>())
                    .build();
            stage = stageService.saveModel(stage);
            stageMap.put(stageName, stage);
            category.getStages().add(stage);
        }
        return stageMap;
    }

    private Set<String> collectStageNames(ImportCompetitionDto.ImportCategoryDto categoryDto) {
        Set<String> stageNames = new HashSet<>();
        for (ImportCompetitionDto.ImportParticipantDto participant : categoryDto.getParticipants()) {
            if (participant.getRuns() != null) {
                for (ImportCompetitionDto.ImportRunDto run : participant.getRuns()) {
                    if (run.getStageName() != null && !run.getStageName().isEmpty()) {
                        stageNames.add(run.getStageName());
                    }
                }
            }
        }
        return stageNames;
    }

    private void createAndSaveParticipantsWithRuns(ImportCompetitionDto.ImportCategoryDto categoryDto, Categorie category, Map<String, Stage> stageMap) {
        for (ImportCompetitionDto.ImportParticipantDto participantDto : categoryDto.getParticipants()) {
            Participant participant = createAndSaveParticipant(participantDto, category);
            createAndSaveRuns(participantDto, participant, stageMap);
            participantService.saveModel(participant);
        }
    }

    private Participant createAndSaveParticipant(ImportCompetitionDto.ImportParticipantDto participantDto, Categorie category) {
        Participant participant = Participant.builder()
                .bibNb(participantDto.getBibNb())
                .name(participantDto.getName())
                .club(participantDto.getClub())
                .categories(new ArrayList<>())
                .runs(new ArrayList<>())
                .build();

        participant.getCategories().add(category);
        category.getParticipants().add(participant);
        return participantService.saveModel(participant);
    }

    private void createAndSaveRuns(ImportCompetitionDto.ImportParticipantDto participantDto, Participant participant, Map<String, Stage> stageMap) {
        if (participantDto.getRuns() != null) {
            for (ImportCompetitionDto.ImportRunDto runDto : participantDto.getRuns()) {
                if (runDto.getStageName() != null && stageMap.containsKey(runDto.getStageName())) {
                    Stage stage = stageMap.get(runDto.getStageName());
                    Run run = Run.builder()
                            .duration(runDto.getDuration())
                            .score((float) runDto.getScore())
                            .stage(stage)
                            .participant(participant)
                            .build();

                    run = runService.saveModel(run);
                    participant.getRuns().add(run);
                    stage.getRuns().add(run);
                }
            }
        }
    }

    // Méthode de validation
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
                if (importDto.getStartDate().compareTo(importDto.getEndDate()) > 0) {
                    errors.add("La date de début ne peut pas être postérieure à la date de fin");
                }
            } catch (Exception e) {
                errors.add("Format de date invalide");
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
            for (ImportCompetitionDto.ImportCategoryDto category : importDto.getCategories()) {
                if (category.getName() != null && !categoryNames.add(category.getName())) {
                    errors.add("Le nom de catégorie '" + category.getName() + "' est en double");
                }
                validateCategory(category, errors);
            }
        }
    }

    private void validateCategory(ImportCompetitionDto.ImportCategoryDto category, List<String> errors) {
        validateCategoryBasics(category, errors);
        if (category.getParticipants() != null) {
            validateParticipants(category, errors);
        }
    }

    private void validateCategoryBasics(ImportCompetitionDto.ImportCategoryDto category, List<String> errors) {
        if (category.getName() == null || category.getName().isEmpty()) {
            errors.add("Le nom de la catégorie est requis");
        }
        if (category.getParticipants() == null || category.getParticipants().isEmpty()) {
            errors.add("Au moins un participant est requis par catégorie");
        }
    }

    private void validateParticipants(ImportCompetitionDto.ImportCategoryDto category, List<String> errors) {
        Set<Integer> bibNumbers = new HashSet<>();
        for (ImportCompetitionDto.ImportParticipantDto participant : category.getParticipants()) {
            validateParticipant(participant, category.getName(), bibNumbers, errors);
        }
    }

    private void validateParticipant(ImportCompetitionDto.ImportParticipantDto participant, String categoryName, Set<Integer> bibNumbers, List<String> errors) {
        validateParticipantBasics(participant, categoryName, bibNumbers, errors);
        validateParticipantRuns(participant, errors);
    }

    private void validateParticipantBasics(ImportCompetitionDto.ImportParticipantDto participant, String categoryName, Set<Integer> bibNumbers, List<String> errors) {
        if (participant.getName() == null || participant.getName().isEmpty()) {
            errors.add("Le nom du participant est requis");
        }
        if (participant.getBibNb() <= 0) {
            errors.add("Le numéro de dossard doit être positif");
        }
        if (participant.getBibNb() > 0 && !bibNumbers.add(participant.getBibNb())) {
            errors.add("Le numéro de dossard " + participant.getBibNb() + " est en double dans la catégorie " + categoryName);
        }
    }

    private void validateParticipantRuns(ImportCompetitionDto.ImportParticipantDto participant, List<String> errors) {
        if (participant.getRuns() == null || participant.getRuns().isEmpty()) {
            errors.add("Le participant " + participant.getName() + " doit avoir au moins un run");
        } else {
            for (ImportCompetitionDto.ImportRunDto run : participant.getRuns()) {
                validateRun(run, errors);
            }
        }
    }

    private void validateRun(ImportCompetitionDto.ImportRunDto run, List<String> errors) {
        if (run.getStageName() == null || run.getStageName().isEmpty()) {
            errors.add("Le nom du stage est requis pour chaque run");
        }
        if (run.getDuration() <= 0) {
            errors.add("La durée du run doit être positive");
        }
        if (run.getScore() < 0) {
            errors.add("Le score du run ne peut pas être négatif");
        }
    }
}
