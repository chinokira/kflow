package kayak.freestyle.competition.kflow.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import kayak.freestyle.competition.kflow.models.Competition;

/**
 * Repository interface for managing Competition entities.
 * Provides methods for finding competitions by various criteria and
 * includes custom queries for fetching compet&itions with their related
 * entities.
 *
 * @author K-FLOW Team
 * @version 1.0
 */
@Repository
public interface CompetitionRepository extends JpaRepository<Competition, Long> {

       /**
        * Finds a competition by its place.
        *
        * @param place The place where the competition is held
        * @return An Optional containing the competition if found, empty otherwise
        */
       Optional<Competition> findByPlace(String place);

       /**
        * Finds a competition by its ID.
        *
        * @param id The ID of the competition
        * @return An Optional containing the competition if found, empty otherwise
        */
       Optional<Competition> findById(int id);

       /**
        * Finds a competition by its ID and eagerly loads all related entities.
        * This includes categories, participants, and their runs.
        *
        * @param id The ID of the competition
        * @return An Optional containing the competition with all related entities if
        *         found, empty otherwise
        */
       @Query("SELECT DISTINCT c FROM Competition c " +
                     "LEFT JOIN FETCH c.categories cat " +
                     "LEFT JOIN FETCH cat.participants p " +
                     "LEFT JOIN FETCH p.runs " +
                     "WHERE c.id = :id")
       Optional<Competition> findCompetitionWithDetails(@Param("id") Long id);

       /**
        * Finds all competitions and eagerly loads their categories.
        * This is an optimized version of findAll() that prevents N+1 query problems.
        *
        * @return A list of all competitions with their categories loaded
        */
       @Query("SELECT DISTINCT c FROM Competition c " +
                     "LEFT JOIN FETCH c.categories")
       List<Competition> findAll();
}
