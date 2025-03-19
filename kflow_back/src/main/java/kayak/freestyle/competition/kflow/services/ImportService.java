package kayak.freestyle.competition.kflow.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kayak.freestyle.competition.kflow.dto.ImportCompetitionDto;
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

    @Transactional
    public Competition importCompetition(ImportCompetitionDto importDto) {
        // Valider l'import avant de commencer
        List<String> errors = validateImport(importDto);
        if (!errors.isEmpty()) {
            throw new IllegalArgumentException("Erreurs de validation : " + String.join(", ", errors));
        }

        // Créer la compétition
        Competition competition = Competition.builder()
                .startDate(importDto.getStartDate())
                .endDate(importDto.getEndDate())
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

    private Stage createStage(String stageName, Categorie category) {
        Stage stage = Stage.builder()
                .name(stageName)
                .nbRun(2) // Valeur par défaut
                .rules("Standard") // Valeur par défaut
                .categorie(category)
                .build();
        return stage;
    }

    private Categorie importCategory(ImportCompetitionDto.ImportCategoryDto categoryDto, Competition competition) {
        // Créer la catégorie
        Categorie category = Categorie.builder()
                .name(categoryDto.getName())
                .competition(competition)
                .build();

        // Sauvegarder la catégorie
        category = categorieService.saveModel(category);

        // Collecter tous les noms de stages uniques
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

        // Créer et sauvegarder les stages
        Map<String, Stage> stageMap = new HashMap<>();
        for (String stageName : stageNames) {
            Stage stage = Stage.builder()
                    .name(stageName)
                    .nbRun(2)
                    .rules("Standard")
                    .categorie(category)
                    .build();
            stage = stageService.saveModel(stage);
            stageMap.put(stageName, stage);
            category.addStage(stage);
        }

        // Sauvegarder la catégorie avec ses stages
        category = categorieService.saveModel(category);

        // Créer et sauvegarder les participants
        List<Participant> participants = new ArrayList<>();
        for (ImportCompetitionDto.ImportParticipantDto participantDto : categoryDto.getParticipants()) {
            // Créer le participant
            Participant participant = Participant.builder()
                    .bibNb(participantDto.getBibNb())
                    .name(participantDto.getName())
                    .club(participantDto.getClub())
                    .categories(new ArrayList<>())
                    .runs(new ArrayList<>())
                    .build();

            // Ajouter la catégorie au participant
            participant.getCategories().add(category);

            // Sauvegarder le participant
            participant = participantService.saveModel(participant);
            participants.add(participant);

            // Créer et sauvegarder les runs
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
                        participant.addRun(run);
                        stage.addRun(run);
                    }
                }
            }

            // Mettre à jour le participant avec ses runs
            participant = participantService.saveModel(participant);
        }

        // Mettre à jour la liste des participants de la catégorie
        for (Participant participant : participants) {
            category.addParticipant(participant);
        }

        // Sauvegarder la catégorie une dernière fois avec tous ses participants
        return categorieService.saveModel(category);
    }

    // Méthode de validation
    public List<String> validateImport(ImportCompetitionDto importDto) {
        List<String> errors = new ArrayList<>();

        // Validation de base
        if (importDto.getStartDate() == null || importDto.getStartDate().isEmpty()) {
            errors.add("La date de début est requise");
        }
        if (importDto.getEndDate() == null || importDto.getEndDate().isEmpty()) {
            errors.add("La date de fin est requise");
        }
        // Validation des dates
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

        if (importDto.getLevel() == null || importDto.getLevel().isEmpty()) {
            errors.add("Le niveau est requis");
        }
        if (importDto.getPlace() == null || importDto.getPlace().isEmpty()) {
            errors.add("Le lieu est requis");
        }
        if (importDto.getCategories() == null || importDto.getCategories().isEmpty()) {
            errors.add("Au moins une catégorie est requise");
        }

        // Validation des catégories
        if (importDto.getCategories() != null) {
            Set<String> categoryNames = new HashSet<>();
            for (ImportCompetitionDto.ImportCategoryDto category : importDto.getCategories()) {
                if (category.getName() != null && !categoryNames.add(category.getName())) {
                    errors.add("Le nom de catégorie '" + category.getName() + "' est en double");
                }
                validateCategory(category, errors);
            }
        }

        return errors;
    }

    private void validateCategory(ImportCompetitionDto.ImportCategoryDto category, List<String> errors) {
        if (category.getName() == null || category.getName().isEmpty()) {
            errors.add("Le nom de la catégorie est requis");
        }
        if (category.getParticipants() == null || category.getParticipants().isEmpty()) {
            errors.add("Au moins un participant est requis par catégorie");
        }

        // Validation des participants et de leurs runs
        if (category.getParticipants() != null) {
            Set<String> stageNames = new HashSet<>();
            Set<Integer> bibNumbers = new HashSet<>();

            // Collecter tous les noms de stages et vérifier les doublons de dossards
            for (ImportCompetitionDto.ImportParticipantDto participant : category.getParticipants()) {
                if (participant.getBibNb() > 0 && !bibNumbers.add(participant.getBibNb())) {
                    errors.add("Le numéro de dossard " + participant.getBibNb() + " est en double dans la catégorie " + category.getName());
                }
                if (participant.getRuns() != null) {
                    for (ImportCompetitionDto.ImportRunDto run : participant.getRuns()) {
                        if (run.getStageName() != null) {
                            stageNames.add(run.getStageName());
                        }
                    }
                }
            }

            // Vérifier que chaque participant a des runs valides
            for (ImportCompetitionDto.ImportParticipantDto participant : category.getParticipants()) {
                if (participant.getName() == null || participant.getName().isEmpty()) {
                    errors.add("Le nom du participant est requis");
                }
                if (participant.getBibNb() <= 0) {
                    errors.add("Le numéro de dossard doit être positif");
                }
                if (participant.getRuns() == null || participant.getRuns().isEmpty()) {
                    errors.add("Le participant " + participant.getName() + " doit avoir au moins un run");
                } else {
                    for (ImportCompetitionDto.ImportRunDto run : participant.getRuns()) {
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
            }
        }
    }
}
