package kayak.freestyle.competition.kflow.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kayak.freestyle.competition.kflow.models.Stage;

/**
 * Repository interface for managing Stage entities.
 * Provides methods for finding stages by their ID or rules.
 * A stage represents a specific phase or round within a competition.
 *
 * @author K-FLOW Team
 * @version 1.0
 */
@Repository
public interface StageRepository extends JpaRepository<Stage, Long> {

    /**
     * Finds a stage by its ID.
     *
     * @param id The ID of the stage
     * @return An Optional containing the stage if found, empty otherwise
     */
    Optional<Stage> findById(int id);

    /**
     * Finds a stage by its rules.
     *
     * @param rules The rules of the stage
     * @return An Optional containing the stage if found, empty otherwise
     */
    Optional<Stage> findByRules(String rules);
}
