package kayak.freestyle.competition.kflow.controllers;

import java.util.Collection;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import kayak.freestyle.competition.kflow.dto.HasId;
import kayak.freestyle.competition.kflow.exceptions.BadRequestException;
import kayak.freestyle.competition.kflow.services.GenericService;
import lombok.RequiredArgsConstructor;

/**
 * Generic REST controller that provides basic CRUD operations. This controller
 * can be extended to create specific controllers for different entities.
 *
 * @param <DTO> The DTO type that extends HasId
 * @param <SERVICE> The service type that extends GenericService
 * @author K-FLOW Team
 * @version 1.0
 */
@RequiredArgsConstructor
public class GenericController<DTO extends HasId, SERVICE extends GenericService<?, DTO, ?, ?>> {

    /**
     * The service instance that handles the business logic.
     */
    protected final SERVICE service;

    /**
     * Retrieves all entities and returns them as DTOs.
     *
     * @return A collection of DTOs representing all entities
     */
    @GetMapping
    public Collection<DTO> findAll() {
        return service.findAll();
    }

    /**
     * Retrieves a single entity by its ID and returns it as a DTO.
     *
     * @param id The ID of the entity to retrieve
     * @return The DTO representing the found entity
     */
    @GetMapping("{id}")
    public DTO findByIdDto(@PathVariable long id) {
        return service.findByIdDto(id);
    }

    /**
     * Creates a new entity from the provided DTO. The ID in the DTO must be
     * null or zero.
     *
     * @param user The DTO containing the entity data
     * @return The created entity as a DTO
     * @throws BadRequestException if the ID is not null or zero
     */
    @PostMapping
    public DTO save(@Valid @RequestBody DTO user) {
        if (user.getId() != 0) {
            throw new BadRequestException("id must be null or zero");
        }
        return service.save(user);
    }

    /**
     * Updates an existing entity with the provided DTO. The ID in the URL must
     * match the ID in the DTO.
     *
     * @param id The ID of the entity to update
     * @param user The DTO containing the updated entity data
     * @throws BadRequestException if the IDs don't match
     */
    @Transactional
    @PutMapping("{id}")
    public void update(@PathVariable long id, @Valid @RequestBody DTO user) {
        if (user.getId() == 0) {
            user.setId(id);
        } else if (user.getId() != id) {
            throw new BadRequestException("id in url and body must be the same");
        }
        service.save(user);
    }

    /**
     * Deletes an entity by its ID.
     *
     * @param id The ID of the entity to delete
     */
    @Transactional
    @DeleteMapping("{id}")
    public void deleteById(@PathVariable long id) {
        service.deleteById(id);
    }
}
