package kayak.freestyle.competition.kflow.services;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.repository.JpaRepository;

import kayak.freestyle.competition.kflow.dto.HasId;
import kayak.freestyle.competition.kflow.exceptions.NotFoundException;
import kayak.freestyle.competition.kflow.mappers.GenericMapper;

@ExtendWith(MockitoExtension.class)
@SuppressWarnings("null")
class GenericServiceTest {

    @Mock
    private JpaRepository<TestModel, Long> repository;

    @Mock
    private GenericMapper<TestModel, TestDto> mapper;

    private GenericService<TestModel, TestDto, JpaRepository<TestModel, Long>, GenericMapper<TestModel, TestDto>> service;

    private TestModel testModel;
    private TestDto testDto;

    @BeforeEach
    void setUp() {
        service = new GenericService<>(repository, mapper);
        testModel = new TestModel();
        testModel.setId(1L);
        testModel.setName("Test Model");

        testDto = new TestDto();
        testDto.setId(1L);
        testDto.setName("Test DTO");
    }

    @SuppressWarnings("null")
    @Test
    void findAll_ShouldReturnAllDtos() {
        List<TestModel> models = Arrays.asList(testModel);
        when(repository.findAll()).thenReturn(models);
        when(mapper.modelToDto(any(TestModel.class))).thenReturn(testDto);

        List<TestDto> result = service.findAll().stream().toList();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testDto, result.get(0));
        verify(repository).findAll();
        verify(mapper).modelToDto(testModel);
    }

    @SuppressWarnings("null")
    @Test
    void findByIdDto_WithExistingId_ShouldReturnDto() {
        when(repository.findById(1L)).thenReturn(Optional.of(testModel));
        when(mapper.modelToDto(testModel)).thenReturn(testDto);

        TestDto result = service.findByIdDto(1L);

        assertNotNull(result);
        assertEquals(testDto, result);
        verify(repository).findById(1L);
        verify(mapper).modelToDto(testModel);
    }

    @SuppressWarnings("null")
    @Test
    void findByIdDto_WithNonExistingId_ShouldThrowException() {
        when(repository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> service.findByIdDto(999L));
        verify(repository).findById(999L);
    }

    @SuppressWarnings("null")
    @Test
    void findById_WithExistingId_ShouldReturnModel() {
        when(repository.findById(1L)).thenReturn(Optional.of(testModel));

        TestModel result = service.findById(1L);

        assertNotNull(result);
        assertEquals(testModel, result);
        verify(repository).findById(1L);
    }

    @SuppressWarnings("null")
    @Test
    void findById_WithNonExistingId_ShouldThrowException() {
        when(repository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> service.findById(999L));
        verify(repository).findById(999L);
    }

    @SuppressWarnings("null")
    @Test
    void save_ShouldSaveAndReturnDto() {
        when(mapper.dtoToModel(testDto)).thenReturn(testModel);
        when(repository.save(testModel)).thenReturn(testModel);
        when(mapper.modelToDto(testModel)).thenReturn(testDto);

        TestDto result = service.save(testDto);

        assertNotNull(result);
        assertEquals(testDto, result);
        verify(mapper).dtoToModel(testDto);
        verify(repository).save(testModel);
        verify(mapper).modelToDto(testModel);
    }

    @SuppressWarnings("null")
    @Test
    void update_WithExistingId_ShouldUpdateModel() {
        when(repository.findById(1L)).thenReturn(Optional.of(testModel));
        when(mapper.dtoToModel(testDto)).thenReturn(testModel);
        when(repository.save(testModel)).thenReturn(testModel);

        service.update(testDto);

        verify(repository).findById(1L);
        verify(mapper).dtoToModel(testDto);
        verify(repository).save(testModel);
    }

    @SuppressWarnings("null")
    @Test
    void update_WithNonExistingId_ShouldThrowException() {
        TestDto dto = new TestDto();
        dto.setId(999L);
        lenient().when(repository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> service.update(dto));
        verify(repository).findById(999L);
    }

    @SuppressWarnings("null")
    @Test
    void deleteById_WithExistingId_ShouldDeleteModel() {
        when(repository.findById(1L)).thenReturn(Optional.of(testModel));

        service.deleteById(1L);

        verify(repository).findById(1L);
        verify(repository).deleteById(1L);
    }

    @SuppressWarnings("null")
    @Test
    void deleteById_WithNonExistingId_ShouldThrowException() {
        when(repository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> service.deleteById(999L));
        verify(repository).findById(999L);
    }

    @SuppressWarnings("null")
    @Test
    void saveModel_ShouldSaveAndReturnModel() {
        when(repository.save(testModel)).thenReturn(testModel);

        TestModel result = service.saveModel(testModel);

        assertNotNull(result);
        assertEquals(testModel, result);
        verify(repository).save(testModel);
    }

    // Classes de test internes
    @SuppressWarnings("unused")
    private static class TestModel {

        private Long id;
        private String name;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    @SuppressWarnings("unused")
    private static class TestDto implements HasId {

        private Long id;
        private String name;

        @Override
        public Long getId() {
            return id;
        }

        @Override
        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
