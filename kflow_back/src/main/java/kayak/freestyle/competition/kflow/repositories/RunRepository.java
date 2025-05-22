package kayak.freestyle.competition.kflow.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kayak.freestyle.competition.kflow.models.Run;

/**
 * Repository interface for managing Run entities.
 * Provides methods for finding runs by their ID.
 * A run represents a single attempt by a participant in a competition stage.
 *
 * @author K-FLOW Team
 * @version 1.0
 */
@Repository
public interface RunRepository extends JpaRepository<Run, Long> {

    /**
     * Finds a run by its ID.
     *
     * @param id The ID of the run
     * @return An Optional containing the run if found, empty otherwise
     */
    Optional<Run> findById(int id);
}
