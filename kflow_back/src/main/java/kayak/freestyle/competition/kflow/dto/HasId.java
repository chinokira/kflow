package kayak.freestyle.competition.kflow.dto;

/**
 * Interface that defines the contract for DTOs that have an ID.
 * This interface is used to ensure that all DTOs used in generic operations
 * have the ability to get and set their ID.
 *
 * @author K-FLOW Team
 * @version 1.0
 */
public interface HasId {

    /**
     * Gets the ID of the DTO.
     *
     * @return The ID of the DTO
     */
    Long getId();

    /**
     * Sets the ID of the DTO.
     *
     * @param id The ID to set
     */
    void setId(Long id);
}
