package kayak.freestyle.competition.kflow.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import kayak.freestyle.competition.kflow.models.Participant;

/**
 * Repository interface for managing Participant entities.
 * Provides methods for finding participants and their runs,
 * with optimized queries to prevent N+1 query problems.
 *
 * @author K-FLOW Team
 * @version 1.0
 */
@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long> {

    /**
     * Finds a participant by its ID.
     *
     * @param id The ID of the participant
     * @return An Optional containing the participant if found, empty otherwise
     */
    Optional<Participant> findById(int id);

    /**
     * Finds a participant by its ID and eagerly loads its runs.
     * This is an optimized query that prevents N+1 query problems.
     *
     * @param id The ID of the participant
     * @return An Optional containing the participant with its runs if found, empty otherwise
     */
    @Query("SELECT p FROM Participant p LEFT JOIN FETCH p.runs WHERE p.id = :id")
    Optional<Participant> findByIdAndFetchRunsEagerly(@Param("id") Long id);
}
