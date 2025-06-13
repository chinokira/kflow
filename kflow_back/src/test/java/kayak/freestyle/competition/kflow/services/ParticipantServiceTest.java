package kayak.freestyle.competition.kflow.services;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import kayak.freestyle.competition.kflow.dto.ParticipantDto;
import kayak.freestyle.competition.kflow.dto.RunDto;
import kayak.freestyle.competition.kflow.mappers.ParticipantMapper;
import kayak.freestyle.competition.kflow.mappers.RunMapper;
import kayak.freestyle.competition.kflow.models.Participant;
import kayak.freestyle.competition.kflow.models.Run;
import kayak.freestyle.competition.kflow.repositories.ParticipantRepository;

@ExtendWith(MockitoExtension.class)
class ParticipantServiceTest {

    @Mock
    private ParticipantRepository repository;

    @Mock
    private ParticipantMapper participantMapper;

    @Mock
    private RunMapper runMapper;

    private ParticipantService participantService;

    private Participant participant;
    private ParticipantDto participantDto;
    private Run run;
    private RunDto runDto;

    @BeforeEach
    void setUp() {
        participantService = new ParticipantService(repository, participantMapper, runMapper);

        participant = new Participant();
        participant.setId(1L);
        participant.setName("John Doe");
        participant.setBibNb(123);
        participant.setClub("Club A");

        participantDto = new ParticipantDto();
        participantDto.setId(1L);
        participantDto.setName("John Doe");
        participantDto.setBibNb(123);
        participantDto.setClub("Club A");

        run = new Run();
        run.setId(1L);
        run.setScore(100.0f);
        run.setDuration(120);

        runDto = new RunDto();
        runDto.setId(1L);
        runDto.setScore(100.0f);
        runDto.setDuration(120);
    }

    @Test
    void getRunsByParticipantId_WithExistingId_ShouldReturnRuns() {
        Set<Run> runs = new HashSet<>();
        runs.add(run);
        participant.setRuns(runs);
        when(repository.findByIdAndFetchRunsEagerly(1L)).thenReturn(Optional.of(participant));
        when(runMapper.modelToDto(run)).thenReturn(runDto);

        List<RunDto> result = participantService.getRunsByParticipantId(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(runDto, result.get(0));
        verify(repository).findByIdAndFetchRunsEagerly(1L);
        verify(runMapper).modelToDto(run);
    }

    @Test
    void getRunsByParticipantId_WithNonExistingId_ShouldThrowException() {
        when(repository.findByIdAndFetchRunsEagerly(999L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> participantService.getRunsByParticipantId(999L));
        verify(repository).findByIdAndFetchRunsEagerly(999L);
    }

    @Test
    void getRunsByParticipantId_WithNoRuns_ShouldReturnEmptyList() {
        participant.setRuns(new HashSet<>());
        when(repository.findByIdAndFetchRunsEagerly(1L)).thenReturn(Optional.of(participant));

        List<RunDto> result = participantService.getRunsByParticipantId(1L);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(repository).findByIdAndFetchRunsEagerly(1L);
    }

    @Test
    void save_ShouldSaveAndReturnDto() {
        when(participantMapper.dtoToModel(participantDto)).thenReturn(participant);
        when(repository.save(participant)).thenReturn(participant);
        when(participantMapper.modelToDto(participant)).thenReturn(participantDto);

        ParticipantDto result = participantService.save(participantDto);

        assertNotNull(result);
        assertEquals(participantDto, result);
        verify(participantMapper).dtoToModel(participantDto);
        verify(repository).save(participant);
        verify(participantMapper).modelToDto(participant);
    }

    @Test
    void save_WithNullName_ShouldSaveAndReturnDto() {
        participantDto.setName(null);
        when(participantMapper.dtoToModel(participantDto)).thenReturn(participant);
        when(repository.save(participant)).thenReturn(participant);
        when(participantMapper.modelToDto(participant)).thenReturn(participantDto);

        ParticipantDto result = participantService.save(participantDto);

        assertNotNull(result);
        assertEquals(participantDto, result);
        verify(participantMapper).dtoToModel(participantDto);
        verify(repository).save(participant);
        verify(participantMapper).modelToDto(participant);
    }

    @Test
    void save_WithZeroBibNb_ShouldSaveAndReturnDto() {
        participantDto.setBibNb(0);
        when(participantMapper.dtoToModel(participantDto)).thenReturn(participant);
        when(repository.save(participant)).thenReturn(participant);
        when(participantMapper.modelToDto(participant)).thenReturn(participantDto);

        ParticipantDto result = participantService.save(participantDto);

        assertNotNull(result);
        assertEquals(participantDto, result);
        verify(participantMapper).dtoToModel(participantDto);
        verify(repository).save(participant);
        verify(participantMapper).modelToDto(participant);
    }

    @Test
    void save_WithNullClub_ShouldSaveAndReturnDto() {
        participantDto.setClub(null);
        when(participantMapper.dtoToModel(participantDto)).thenReturn(participant);
        when(repository.save(participant)).thenReturn(participant);
        when(participantMapper.modelToDto(participant)).thenReturn(participantDto);

        ParticipantDto result = participantService.save(participantDto);

        assertNotNull(result);
        assertEquals(participantDto, result);
        verify(participantMapper).dtoToModel(participantDto);
        verify(repository).save(participant);
        verify(participantMapper).modelToDto(participant);
    }
}
