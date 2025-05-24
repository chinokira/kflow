package kayak.freestyle.competition.kflow.mappers;

import kayak.freestyle.competition.kflow.dto.HasId;

/**
 * Generic mapper interface that defines methods for converting between domain models and DTOs.
 * This interface should be implemented by all specific mappers in the application.
 *
 * @param <MODEL> The domain model type
 * @param <DTO> The DTO type that extends HasId
 * @author K-FLOW Team
 * @version 1.0
 */
public interface GenericMapper<MODEL, DTO extends HasId> {

    /**
     * Converts a domain model to its corresponding DTO.
     *
     * @param m The domain model to convert
     * @return The corresponding DTO
     */
    DTO modelToDto(MODEL m);

    /**
     * Converts a DTO to its corresponding domain model.
     *
     * @param d The DTO to convert
     * @return The corresponding domain model
     */
    MODEL dtoToModel(DTO d);
}
