package kayak.freestyle.competition.kflow.integration;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import kayak.freestyle.competition.kflow.dto.CategorieDto;
import kayak.freestyle.competition.kflow.dto.CompetitionDto;
import kayak.freestyle.competition.kflow.dto.ParticipantDto;
import kayak.freestyle.competition.kflow.dto.RunDto;
import kayak.freestyle.competition.kflow.dto.StageDto;
import kayak.freestyle.competition.kflow.models.Categorie;
import kayak.freestyle.competition.kflow.models.Competition;
import kayak.freestyle.competition.kflow.models.Participant;
import kayak.freestyle.competition.kflow.models.Run;
import kayak.freestyle.competition.kflow.services.CompetitionService;
import kayak.freestyle.competition.kflow.services.ImportService;

@SpringBootTest
@Transactional
class CompetitionImportIntegrationTest {

    @Autowired
    private ImportService importService;

    @Autowired
    private CompetitionService competitionService;

    @Test
    void importCompetition_WithValidData_ShouldCreateCompleteCompetition() {
        // Given
        CompetitionDto competitionDto = createValidCompetitionDto();

        // When
        Competition savedCompetition = importService.importCompetition(competitionDto);

        // Then
        assertNotNull(savedCompetition);
        assertNotNull(savedCompetition.getId());
        assertEquals(competitionDto.getStartDate(), savedCompetition.getStartDate());
        assertEquals(competitionDto.getEndDate(), savedCompetition.getEndDate());
        assertEquals(competitionDto.getLevel(), savedCompetition.getLevel());
        assertEquals(competitionDto.getPlace(), savedCompetition.getPlace());

        // Vérifier les catégories
        Set<Categorie> categories = savedCompetition.getCategories();
        assertNotNull(categories);
        assertEquals(1, categories.size());
        Categorie category = categories.iterator().next();
        assertEquals("Senior", category.getName());

        // Vérifier les participants
        Set<Participant> participants = category.getParticipants();
        assertNotNull(participants);
        assertEquals(1, participants.size());
        Participant participant = participants.iterator().next();
        assertEquals("John Doe", participant.getName());
        assertEquals(123, participant.getBibNb());
        assertEquals("Club A", participant.getClub());

        // Vérifier les runs
        Set<Run> runs = participant.getRuns();
        assertNotNull(runs);
        assertEquals(1, runs.size());
        Run run = runs.iterator().next();
        assertEquals(100.0f, run.getScore());
        assertEquals(120, run.getDuration());
        assertNotNull(run.getStage());
        assertEquals("Final", run.getStage().getName());
    }

    private CompetitionDto createValidCompetitionDto() {
        CompetitionDto competitionDto = new CompetitionDto();
        competitionDto.setStartDate(LocalDate.parse("2024-01-01"));
        competitionDto.setEndDate(LocalDate.parse("2024-01-02"));
        competitionDto.setLevel("PRO");
        competitionDto.setPlace("Paris");

        CategorieDto categorie = new CategorieDto();
        categorie.setName("Senior");

        ParticipantDto participant = new ParticipantDto();
        participant.setName("John Doe");
        participant.setBibNb(123);
        participant.setClub("Club A");

        RunDto run = new RunDto();
        run.setScore(100.0f);
        run.setDuration(120);
        StageDto stage = new StageDto();
        stage.setName("Final");
        run.setStage(stage);

        participant.setRuns(List.of(run));
        categorie.setParticipants(List.of(participant));
        competitionDto.setCategories(List.of(categorie));

        return competitionDto;
    }
}
