package kayak.freestyle.competition.kflow.services;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import kayak.freestyle.competition.kflow.dto.CategorieDto;
import kayak.freestyle.competition.kflow.dto.CompetitionDto;
import kayak.freestyle.competition.kflow.dto.ParticipantDto;
import kayak.freestyle.competition.kflow.dto.RunDto;
import kayak.freestyle.competition.kflow.dto.StageDto;
import kayak.freestyle.competition.kflow.mappers.CategorieMapper;
import kayak.freestyle.competition.kflow.mappers.ParticipantMapper;
import kayak.freestyle.competition.kflow.mappers.RunMapper;
import kayak.freestyle.competition.kflow.mappers.StageMapper;
import kayak.freestyle.competition.kflow.models.Competition;
import kayak.freestyle.competition.kflow.repositories.CompetitionRepository;

@ExtendWith(MockitoExtension.class)
class ImportServiceTest {

    @Mock
    private CompetitionService competitionService;

    @Mock
    private CompetitionRepository competitionRepository;

    @Mock
    private CategorieService categorieService;

    @Mock
    private StageService stageService;

    @Mock
    private ParticipantService participantService;

    @Mock
    private RunService runService;

    @Mock
    private CategorieMapper categorieMapper;

    @Mock
    private ParticipantMapper participantMapper;

    @Mock
    private StageMapper stageMapper;

    @Mock
    private RunMapper runMapper;

    @InjectMocks
    private ImportService importService;

    private CompetitionDto validImportDto;

    @BeforeEach
    void setUp() {
        validImportDto = new CompetitionDto();
        validImportDto.setStartDate(LocalDate.parse("2024-01-01"));
        validImportDto.setEndDate(LocalDate.parse("2024-01-02"));
        validImportDto.setLevel("PRO");
        validImportDto.setPlace("Paris");

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
        validImportDto.setCategories(List.of(categorie));
    }

    @Test
    void validateImport_WithValidData_ShouldReturnEmptyErrors() {
        List<String> errors = importService.validateImport(validImportDto);
        assertTrue(errors.isEmpty(), "Should not have any validation errors");
    }

    @Test
    void validateImport_WithInvalidDates_ShouldReturnErrors() {
        validImportDto.setStartDate(LocalDate.parse("2024-01-02"));
        validImportDto.setEndDate(LocalDate.parse("2024-01-01"));

        List<String> errors = importService.validateImport(validImportDto);
        assertFalse(errors.isEmpty(), "Should have validation errors for invalid dates");
        assertTrue(errors.stream().anyMatch(error -> error.contains("date")), "Should contain date-related error");
    }

    @Test
    void validateImport_WithMissingRequiredFields_ShouldReturnErrors() {
        validImportDto.setLevel(null);
        validImportDto.setPlace(null);

        List<String> errors = importService.validateImport(validImportDto);
        assertFalse(errors.isEmpty(), "Should have validation errors for missing fields");
        assertTrue(errors.stream().anyMatch(error -> error.contains("requis")), "Should contain required field error");
    }

    @Test
    void importCompetition_WithValidData_ShouldSaveCompetition() {
        Competition savedCompetition = new Competition();
        when(competitionService.saveModel(any(Competition.class))).thenReturn(savedCompetition);
        kayak.freestyle.competition.kflow.models.Categorie mockCategorie = new kayak.freestyle.competition.kflow.models.Categorie();
        mockCategorie.setId(1L);
        when(categorieService.saveModel(any())).thenReturn(mockCategorie);
        kayak.freestyle.competition.kflow.models.Participant mockParticipant = new kayak.freestyle.competition.kflow.models.Participant();
        mockParticipant.setId(1L);
        when(participantMapper.dtoToModel(any())).thenReturn(mockParticipant);
        when(participantService.saveModel(any())).thenReturn(mockParticipant);
        kayak.freestyle.competition.kflow.models.Stage mockStage = new kayak.freestyle.competition.kflow.models.Stage();
        mockStage.setId(1L);
        mockStage.setName("Final");
        when(stageMapper.dtoToModel(any())).thenReturn(mockStage);
        when(stageService.saveModel(any())).thenReturn(mockStage);
        kayak.freestyle.competition.kflow.models.Run mockRun = new kayak.freestyle.competition.kflow.models.Run();
        mockRun.setId(1L);
        when(runMapper.dtoToModel(any())).thenReturn(mockRun);
        when(runService.saveModel(any())).thenReturn(mockRun);

        Competition result = importService.importCompetition(validImportDto);

        assertNotNull(result);
        verify(competitionService).saveModel(any(Competition.class));
    }
}
