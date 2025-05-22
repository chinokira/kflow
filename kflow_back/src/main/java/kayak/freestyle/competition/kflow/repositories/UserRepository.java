package kayak.freestyle.competition.kflow.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kayak.freestyle.competition.kflow.models.User;

/**
 * Repository interface for managing User entities.
 * Provides methods for finding users by their email or ID.
 * Used primarily for authentication and user management.
 *
 * @author K-FLOW Team
 * @version 1.0
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finds a user by their email address.
     * Used for user authentication and lookup.
     *
     * @param email The email address of the user
     * @return An Optional containing the user if found, empty otherwise
     */
    Optional<User> findByEmail(String email);

    /**
     * Finds a user by their ID.
     *
     * @param id The ID of the user
     * @return An Optional containing the user if found, empty otherwise
     */
    Optional<User> findById(int id);
}
