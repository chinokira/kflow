package kayak.freestyle.competition.kflow.services;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import jakarta.transaction.Transactional;
import kayak.freestyle.competition.kflow.dto.HasId;
import kayak.freestyle.competition.kflow.exceptions.NotFoundException;
import kayak.freestyle.competition.kflow.mappers.GenericMapper;
import lombok.RequiredArgsConstructor;

/**
 * Generic service that provides basic CRUD operations for any entity.
 * This service handles the conversion between DTOs and domain models,
 * and delegates the actual data operations to a JPA repository.
 *
 * @param <MODEL> The domain model type
 * @param <DTO> The DTO type that extends HasId
 * @param <REPOSITORY> The repository type that extends JpaRepository
 * @param <MAPPER> The mapper type that extends GenericMapper
 * @author K-FLOW Team
 * @version 1.0
 */
@RequiredArgsConstructor
public class GenericService<MODEL, DTO extends HasId, REPOSITORY extends JpaRepository<MODEL, Long>, MAPPER extends GenericMapper<MODEL, DTO>> {

    /**
     * The repository instance that handles data persistence.
     */
    protected final REPOSITORY repository;

    /**
     * The mapper instance that handles conversion between models and DTOs.
     */
    protected final MAPPER mapper;

    /**
     * Retrieves all entities and converts them to DTOs.
     *
     * @return A collection of DTOs representing all entities
     */
    public Collection<DTO> findAll() {
        return repository.findAll().stream()
                .map(mapper::modelToDto)
                .toList();
    }

    /**
     * Retrieves a single entity by its ID and converts it to a DTO.
     *
     * @param id The ID of the entity to retrieve
     * @return The DTO representing the found entity
     * @throws NotFoundException if no entity exists with the given ID
     */
    public DTO findByIdDto(long id) {
        return repository.findById(id)
                .map(mapper::modelToDto)
                .orElseThrow(() -> new NotFoundException("no entity with id " + id + " exists"));
    }

    /**
     * Retrieves a single entity by its ID.
     *
     * @param id The ID of the entity to retrieve
     * @return The found entity
     * @throws IllegalArgumentException if no entity exists with the given ID
     */
    public MODEL findById(long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Run not found with ID: " + id));
    }

    /**
     * Saves a new entity from a DTO.
     *
     * @param dto The DTO containing the entity data
     * @return The saved entity as a DTO
     */
    public DTO save(DTO dto) {
        return mapper.modelToDto(repository.save(mapper.dtoToModel(dto)));
    }

    /**
     * Updates an existing entity from a DTO.
     *
     * @param dto The DTO containing the updated entity data
     * @throws NotFoundException if no entity exists with the given ID
     */
    public void update(DTO dto) {
        throwIfNotExist(dto.getId());
        repository.save(mapper.dtoToModel(dto));
    }

    /**
     * Deletes an entity by its ID.
     *
     * @param id The ID of the entity to delete
     * @throws NotFoundException if no entity exists with the given ID
     */
    @Transactional
    public void deleteById(long id) {
        throwIfNotExist(id);
        repository.deleteById(id);
    }

    /**
     * Checks if an entity exists with the given ID.
     * Throws a NotFoundException if no entity is found.
     *
     * @param id The ID to check
     * @throws NotFoundException if no entity exists with the given ID
     */
    protected void throwIfNotExist(long id) {
        if (repository.findById(id).isEmpty()) {
            throw new NotFoundException("no entity with id " + id + " exists");
        }
    }

    /**
     * Saves a model entity directly without DTO conversion.
     *
     * @param model The model entity to save
     * @return The saved model entity
     */
    public MODEL saveModel(MODEL model) {
        return repository.save(model);
    }
}
