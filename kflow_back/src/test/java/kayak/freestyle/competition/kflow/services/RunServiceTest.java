package kayak.freestyle.competition.kflow.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import kayak.freestyle.competition.kflow.dto.RunDto;
import kayak.freestyle.competition.kflow.mappers.RunMapper;
import kayak.freestyle.competition.kflow.models.Participant;
import kayak.freestyle.competition.kflow.models.Run;
import kayak.freestyle.competition.kflow.models.Stage;
import kayak.freestyle.competition.kflow.repositories.RunRepository;

@ExtendWith(MockitoExtension.class)
class RunServiceTest {

    @Mock
    private RunRepository repository;

    @Mock
    private RunMapper mapper;

    private RunService runService;

    private Run run;
    private RunDto runDto;
    private Participant participant;
    private Stage stage;

    @BeforeEach
    void setUp() {
        runService = new RunService(repository, mapper);
        
        participant = new Participant();
        participant.setId(1L);
        participant.setName("John Doe");
        participant.setBibNb(123);
        participant.setClub("Club A");

        stage = new Stage();
        stage.setId(1L);
        stage.setName("Final");

        run = new Run();
        run.setId(1L);
        run.setScore(100.0f);
        run.setDuration(120);
        run.setParticipant(participant);
        run.setStage(stage);

        runDto = new RunDto();
        runDto.setId(1L);
        runDto.setScore(100.0f);
        runDto.setDuration(120);
    }

    @Test
    void save_ShouldSaveAndReturnDto() {
        when(mapper.dtoToModel(runDto)).thenReturn(run);
        when(repository.save(run)).thenReturn(run);
        when(mapper.modelToDto(run)).thenReturn(runDto);

        RunDto result = runService.save(runDto);

        assertNotNull(result);
        assertEquals(runDto, result);
        verify(mapper).dtoToModel(runDto);
        verify(repository).save(run);
        verify(mapper).modelToDto(run);
    }

    @Test
    void save_WithNegativeScore_ShouldSaveAndReturnDto() {
        runDto.setScore(-1.0f);
        when(mapper.dtoToModel(runDto)).thenReturn(run);
        when(repository.save(run)).thenReturn(run);
        when(mapper.modelToDto(run)).thenReturn(runDto);

        RunDto result = runService.save(runDto);

        assertNotNull(result);
        assertEquals(runDto, result);
        verify(mapper).dtoToModel(runDto);
        verify(repository).save(run);
        verify(mapper).modelToDto(run);
    }

    @Test
    void save_WithNegativeDuration_ShouldSaveAndReturnDto() {
        runDto.setDuration(-1);
        when(mapper.dtoToModel(runDto)).thenReturn(run);
        when(repository.save(run)).thenReturn(run);
        when(mapper.modelToDto(run)).thenReturn(runDto);

        RunDto result = runService.save(runDto);

        assertNotNull(result);
        assertEquals(runDto, result);
        verify(mapper).dtoToModel(runDto);
        verify(repository).save(run);
        verify(mapper).modelToDto(run);
    }

    @Test
    void save_WithNullParticipant_ShouldSaveAndReturnDto() {
        run.setParticipant(null);
        when(mapper.dtoToModel(runDto)).thenReturn(run);
        when(repository.save(run)).thenReturn(run);
        when(mapper.modelToDto(run)).thenReturn(runDto);

        RunDto result = runService.save(runDto);

        assertNotNull(result);
        assertEquals(runDto, result);
        verify(mapper).dtoToModel(runDto);
        verify(repository).save(run);
        verify(mapper).modelToDto(run);
    }

    @Test
    void save_WithNullStage_ShouldSaveAndReturnDto() {
        run.setStage(null);
        when(mapper.dtoToModel(runDto)).thenReturn(run);
        when(repository.save(run)).thenReturn(run);
        when(mapper.modelToDto(run)).thenReturn(runDto);

        RunDto result = runService.save(runDto);

        assertNotNull(result);
        assertEquals(runDto, result);
        verify(mapper).dtoToModel(runDto);
        verify(repository).save(run);
        verify(mapper).modelToDto(run);
    }

    @Test
    void save_WithZeroScore_ShouldBeValid() {
        runDto.setScore(0.0f);
        when(mapper.dtoToModel(runDto)).thenReturn(run);
        when(repository.save(run)).thenReturn(run);
        when(mapper.modelToDto(run)).thenReturn(runDto);

        RunDto result = runService.save(runDto);

        assertNotNull(result);
        assertEquals(0.0f, result.getScore());
        verify(repository).save(any(Run.class));
    }

    @Test
    void save_WithZeroDuration_ShouldBeValid() {
        runDto.setDuration(0);
        when(mapper.dtoToModel(runDto)).thenReturn(run);
        when(repository.save(run)).thenReturn(run);
        when(mapper.modelToDto(run)).thenReturn(runDto);

        RunDto result = runService.save(runDto);

        assertNotNull(result);
        assertEquals(0, result.getDuration());
        verify(repository).save(any(Run.class));
    }
} 