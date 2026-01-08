package kayak.freestyle.competition.kflow.services;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import kayak.freestyle.competition.kflow.dto.CompetitionDto;
import kayak.freestyle.competition.kflow.dto.UpdateCompetitionDto;
import kayak.freestyle.competition.kflow.mappers.CompetitionMapper;
import kayak.freestyle.competition.kflow.models.Competition;
import kayak.freestyle.competition.kflow.repositories.CompetitionRepository;

@ExtendWith(MockitoExtension.class)
@SuppressWarnings("null")
class CompetitionServiceTest {

    @Mock
    private CompetitionRepository repository;

    @Mock
    private CompetitionMapper mapper;

    private CompetitionService competitionService;

    private Competition competition;
    private CompetitionDto competitionDto;
    private UpdateCompetitionDto updateDto;

    @BeforeEach
    void setUp() {
        competitionService = new CompetitionService(repository, mapper);

        competition = new Competition();
        competition.setId(1L);
        competition.setStartDate(LocalDate.parse("2024-01-01"));
        competition.setEndDate(LocalDate.parse("2024-01-02"));
        competition.setLevel("PRO");
        competition.setPlace("Paris");

        competitionDto = new CompetitionDto();
        competitionDto.setId(1L);
        competitionDto.setStartDate(LocalDate.parse("2024-01-01"));
        competitionDto.setEndDate(LocalDate.parse("2024-01-02"));
        competitionDto.setLevel("PRO");
        competitionDto.setPlace("Paris");

        updateDto = new UpdateCompetitionDto();
        updateDto.setStartDate("2024-01-01");
        updateDto.setEndDate("2024-01-02");
        updateDto.setLevel("PRO");
        updateDto.setPlace("Paris");
    }

    @SuppressWarnings("null")
    @Test
    void getCompetitionWithDetails_WithExistingId_ShouldReturnDto() {
        when(repository.findCompetitionWithDetails(1L)).thenReturn(Optional.of(competition));
        when(mapper.modelToDto(competition)).thenReturn(competitionDto);

        CompetitionDto result = competitionService.getCompetitionWithDetails(1L);

        assertNotNull(result);
        assertEquals(competitionDto, result);
        verify(repository).findCompetitionWithDetails(1L);
        verify(mapper).modelToDto(competition);
    }

    @SuppressWarnings("null")
    @Test
    void getCompetitionWithDetails_WithNonExistingId_ShouldThrowException() {
        when(repository.findCompetitionWithDetails(999L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> competitionService.getCompetitionWithDetails(999L));
        verify(repository).findCompetitionWithDetails(999L);
    }

    @SuppressWarnings("null")
    @Test
    void updateCompetition_WithValidData_ShouldUpdateAndReturnDto() {
        when(repository.findCompetitionWithDetails(1L)).thenReturn(Optional.of(competition));
        when(repository.save(any(Competition.class))).thenReturn(competition);
        when(mapper.modelToDto(any(Competition.class))).thenReturn(competitionDto);

        CompetitionDto result = competitionService.updateCompetition(1L, updateDto);

        assertNotNull(result);
        assertEquals(competitionDto, result);
        verify(repository).findCompetitionWithDetails(1L);
        verify(repository).save(any(Competition.class));
        verify(mapper).modelToDto(any(Competition.class));
    }

    @SuppressWarnings("null")
    @Test
    void updateCompetition_WithNonExistingId_ShouldThrowException() {
        when(repository.findCompetitionWithDetails(999L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> competitionService.updateCompetition(999L, updateDto));
        verify(repository).findCompetitionWithDetails(999L);
    }

    @SuppressWarnings("null")
    @Test
    void updateCompetition_WithInvalidDates_ShouldUpdateAndReturnDto() {
        updateDto.setStartDate("2024-01-02");
        updateDto.setEndDate("2024-01-01");
        when(repository.findCompetitionWithDetails(1L)).thenReturn(Optional.of(competition));
        when(repository.save(any(Competition.class))).thenReturn(competition);
        when(mapper.modelToDto(any(Competition.class))).thenReturn(competitionDto);

        CompetitionDto result = competitionService.updateCompetition(1L, updateDto);

        assertNotNull(result);
        assertEquals(competitionDto, result);
        verify(repository).findCompetitionWithDetails(1L);
        verify(repository).save(any(Competition.class));
        verify(mapper).modelToDto(any(Competition.class));
    }

    @SuppressWarnings("null")
    @Test
    void updateCompetition_WithNullDates_ShouldThrowException() {
        updateDto.setStartDate(null);
        updateDto.setEndDate(null);

        assertThrows(RuntimeException.class, () -> competitionService.updateCompetition(1L, updateDto));
        verify(repository, never()).save(any());
    }

    @SuppressWarnings("null")
    @Test
    void updateCompetition_WithNullLevel_ShouldUpdateAndReturnDto() {
        updateDto.setLevel(null);
        when(repository.findCompetitionWithDetails(1L)).thenReturn(Optional.of(competition));
        when(repository.save(any(Competition.class))).thenReturn(competition);
        when(mapper.modelToDto(any(Competition.class))).thenReturn(competitionDto);

        CompetitionDto result = competitionService.updateCompetition(1L, updateDto);

        assertNotNull(result);
        assertEquals(competitionDto, result);
        verify(repository).findCompetitionWithDetails(1L);
        verify(repository).save(any(Competition.class));
        verify(mapper).modelToDto(any(Competition.class));
    }

    @SuppressWarnings("null")
    @Test
    void updateCompetition_WithNullPlace_ShouldUpdateAndReturnDto() {
        updateDto.setPlace(null);
        when(repository.findCompetitionWithDetails(1L)).thenReturn(Optional.of(competition));
        when(repository.save(any(Competition.class))).thenReturn(competition);
        when(mapper.modelToDto(any(Competition.class))).thenReturn(competitionDto);

        CompetitionDto result = competitionService.updateCompetition(1L, updateDto);

        assertNotNull(result);
        assertEquals(competitionDto, result);
        verify(repository).findCompetitionWithDetails(1L);
        verify(repository).save(any(Competition.class));
        verify(mapper).modelToDto(any(Competition.class));
    }
}
