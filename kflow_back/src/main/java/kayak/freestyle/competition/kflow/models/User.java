package kayak.freestyle.competition.kflow.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * Represents a user in the K-FLOW system.
 * Users can have different roles (e.g., admin, judge, participant)
 * and are used for authentication and authorization purposes.
 *
 * @author K-FLOW Team
 * @version 1.0
 */
@NoArgsConstructor
@SuperBuilder
@Setter
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Entity
public class User {

    /**
     * Unique identifier for the user.
     */
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * The full name of the user.
     */
    private String name;

    /**
     * The email address of the user.
     * Used for login and communication.
     */
    private String email;

    /**
     * The hashed password of the user.
     * Should never be stored in plain text.
     */
    private String password;

    /**
     * The role assigned to the user.
     * Determines the user's permissions and access rights in the system.
     */
    private Role role;
}
