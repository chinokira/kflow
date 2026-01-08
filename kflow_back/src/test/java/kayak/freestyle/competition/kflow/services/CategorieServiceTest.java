package kayak.freestyle.competition.kflow.services;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import kayak.freestyle.competition.kflow.dto.CategorieDto;
import kayak.freestyle.competition.kflow.mappers.CategorieMapper;
import kayak.freestyle.competition.kflow.models.Categorie;
import kayak.freestyle.competition.kflow.models.Competition;
import kayak.freestyle.competition.kflow.models.Participant;
import kayak.freestyle.competition.kflow.repositories.CategorieRepository;

@ExtendWith(MockitoExtension.class)
@SuppressWarnings("null")
class CategorieServiceTest {

    @Mock
    private CategorieRepository repository;

    @Mock
    private CategorieMapper mapper;

    private CategorieService categorieService;

    private Categorie categorie;
    private CategorieDto categorieDto;
    private Competition competition;
    private Participant participant;

    @BeforeEach
    void setUp() {
        categorieService = new CategorieService(repository, mapper);

        competition = new Competition();
        competition.setId(1L);

        participant = new Participant();
        participant.setId(1L);
        participant.setName("John Doe");
        participant.setBibNb(123);
        participant.setClub("Club A");

        categorie = new Categorie();
        categorie.setId(1L);
        categorie.setName("Senior");
        categorie.setCompetition(competition);
        Set<Participant> participants = new HashSet<>();
        participants.add(participant);
        categorie.setParticipants(participants);

        categorieDto = new CategorieDto();
        categorieDto.setId(1L);
        categorieDto.setName("Senior");
    }

    @SuppressWarnings("null")
    @Test
    void getCategorieWithParticipants_WithExistingId_ShouldReturnCategorie() {
        when(repository.findCategorieWithParticipants(1L)).thenReturn(Optional.of(categorie));

        Categorie result = categorieService.getCategorieWithParticipants(1L);

        assertNotNull(result);
        assertEquals(categorie, result);
        assertEquals(1, result.getParticipants().size());
        verify(repository).findCategorieWithParticipants(1L);
    }

    @SuppressWarnings("null")
    @Test
    void getCategorieWithParticipants_WithNonExistingId_ShouldThrowException() {
        when(repository.findCategorieWithParticipants(999L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> categorieService.getCategorieWithParticipants(999L));
        verify(repository).findCategorieWithParticipants(999L);
    }

    @SuppressWarnings("null")
    @Test
    void save_ShouldSaveAndReturnDto() {
        when(mapper.dtoToModel(categorieDto)).thenReturn(categorie);
        when(repository.save(categorie)).thenReturn(categorie);
        when(mapper.modelToDto(categorie)).thenReturn(categorieDto);

        CategorieDto result = categorieService.save(categorieDto);

        assertNotNull(result);
        assertEquals(categorieDto, result);
        verify(mapper).dtoToModel(categorieDto);
        verify(repository).save(categorie);
        verify(mapper).modelToDto(categorie);
    }

    @SuppressWarnings("null")
    @Test
    void save_WithNullName_ShouldSaveAndReturnDto() {
        categorieDto.setName(null);
        when(mapper.dtoToModel(categorieDto)).thenReturn(categorie);
        when(repository.save(categorie)).thenReturn(categorie);
        when(mapper.modelToDto(categorie)).thenReturn(categorieDto);

        CategorieDto result = categorieService.save(categorieDto);

        assertNotNull(result);
        assertEquals(categorieDto, result);
        verify(mapper).dtoToModel(categorieDto);
        verify(repository).save(categorie);
        verify(mapper).modelToDto(categorie);
    }

    @SuppressWarnings("null")
    @Test
    void save_WithEmptyName_ShouldSaveAndReturnDto() {
        categorieDto.setName("");
        when(mapper.dtoToModel(categorieDto)).thenReturn(categorie);
        when(repository.save(categorie)).thenReturn(categorie);
        when(mapper.modelToDto(categorie)).thenReturn(categorieDto);

        CategorieDto result = categorieService.save(categorieDto);

        assertNotNull(result);
        assertEquals(categorieDto, result);
        verify(mapper).dtoToModel(categorieDto);
        verify(repository).save(categorie);
        verify(mapper).modelToDto(categorie);
    }

    @SuppressWarnings("null")
    @Test
    void save_WithNullCompetition_ShouldSaveAndReturnDto() {
        categorie.setCompetition(null);
        when(mapper.dtoToModel(categorieDto)).thenReturn(categorie);
        when(repository.save(categorie)).thenReturn(categorie);
        when(mapper.modelToDto(categorie)).thenReturn(categorieDto);

        CategorieDto result = categorieService.save(categorieDto);

        assertNotNull(result);
        assertEquals(categorieDto, result);
        verify(mapper).dtoToModel(categorieDto);
        verify(repository).save(categorie);
        verify(mapper).modelToDto(categorie);
    }
}
