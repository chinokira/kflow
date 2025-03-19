package kayak.freestyle.competition.kflow.services;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import jakarta.transaction.Transactional;
import kayak.freestyle.competition.kflow.dto.HasId;
import kayak.freestyle.competition.kflow.exceptions.NotFoundException;
import kayak.freestyle.competition.kflow.mappers.GenericMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GenericService<MODEL, DTO extends HasId, REPOSITORY extends JpaRepository<MODEL, Long>, MAPPER extends GenericMapper<MODEL, DTO>> {

    protected final REPOSITORY repository;
    protected final MAPPER mapper;

    public Collection<DTO> findAll() {
        return repository.findAll().stream()
                .map(mapper::modelToDto)
                .toList();
    }

    public DTO findByIdDto(long id) {
        return repository.findById(id)
                .map(mapper::modelToDto)
                .orElseThrow(() -> new NotFoundException("no entity with id " + id + " exists"));
    }

    public MODEL findById(long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Run not found with ID: " + id));
    }

    public DTO save(DTO dto) {
        return mapper.modelToDto(repository.save(mapper.dtoToModel(dto)));
    }

    public void update(DTO dto) {
        throwIfNotExist(dto.getId());
        repository.save(mapper.dtoToModel(dto));
    }

    @Transactional
    public void deleteById(long id) {
        throwIfNotExist(id);
        repository.deleteById(id);
    }

    /**
     * throw exception if repository.findById is empty
     *
     * @param id
     */
    protected void throwIfNotExist(long id) {
        if (repository.findById(id).isEmpty()) {
            throw new NotFoundException("no entity with id " + id + " exists");
        }
    }
}
