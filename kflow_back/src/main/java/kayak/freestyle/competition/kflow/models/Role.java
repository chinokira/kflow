package kayak.freestyle.competition.kflow.models;

/**
 * Defines the possible roles for users in the K-FLOW system.
 * Roles determine the level of access and permissions granted to users.
 *
 * @author K-FLOW Team
 * @version 1.0
 */
public enum Role {
    /**
     * Standard user role with basic access rights.
     * Users with this role can view competitions and their own data.
     */
    USER,

    /**
     * Administrator role with full system access.
     * Administrators can manage competitions, categories, stages,
     * and have access to all system features.
     */
    ADMIN
}
