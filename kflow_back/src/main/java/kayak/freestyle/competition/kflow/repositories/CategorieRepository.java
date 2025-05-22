package kayak.freestyle.competition.kflow.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import kayak.freestyle.competition.kflow.models.Categorie;
import kayak.freestyle.competition.kflow.models.Competition;

/**
 * Repository interface for managing Categorie entities.
 * Provides methods for finding categories by various criteria and
 * includes custom queries for fetching categories with their related participants.
 *
 * @author K-FLOW Team
 * @version 1.0
 */
@Repository
public interface CategorieRepository extends JpaRepository<Categorie, Long> {

    /**
     * Finds a category by its name.
     *
     * @param name The name of the category
     * @return An Optional containing the category if found, empty otherwise
     */
    Optional<Competition> findByName(String name);

    /**
     * Finds a category by its ID.
     *
     * @param id The ID of the category
     * @return An Optional containing the category if found, empty otherwise
     */
    Optional<Competition> findById(int id);

    /**
     * Finds a category by its ID and eagerly loads its participants.
     * This is an optimized query that prevents N+1 query problems.
     *
     * @param id The ID of the category
     * @return An Optional containing the category with its participants if found, empty otherwise
     */
    @Query("SELECT DISTINCT c FROM Categorie c "
            + "LEFT JOIN FETCH c.participants "
            + "WHERE c.id = :id")
    Optional<Categorie> findCategorieWithParticipants(@Param("id") Long id);
}
