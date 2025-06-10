package kayak.freestyle.competition.kflow.models;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * Represents a kayak freestyle competition in the system. This entity stores
 * information about competition dates, location, level, and associated
 * categories.
 *
 * @author K-FLOW Team
 * @version 1.0
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Competition {

    /**
     * Unique identifier for the competition.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    /**
     * The start date of the competition. Cannot be null.
     */
    @Column(nullable = false)
    private LocalDate startDate;

    /**
     * The end date of the competition. Cannot be null.
     */
    @Column(nullable = false)
    private LocalDate endDate;

    /**
     * The location where the competition takes place. Cannot be null.
     */
    @Column(nullable = false)
    private String place;

    /**
     * The level of the competition (e.g., national, international). Cannot be
     * null.
     */
    @Column(nullable = false)
    private String level;

    /**
     * List of categories associated with this competition. Each category
     * represents a different division or class of competition.
     */
    @OneToMany(mappedBy = "competition", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("competition-categories")
    @Column(nullable = true)
    private Set<Categorie> categories = new HashSet<>();

    /**
     * Adds a category to this competition and establishes the bidirectional
     * relationship.
     *
     * @param categorie The category to be added to the competition
     */
    public void addCategorie(Categorie categorie) {
        if (categories == null) {
            categories = new HashSet<>();
        }
        categories.add(categorie);
        categorie.setCompetition(this);
    }

    /**
     * Removes a category from this competition and breaks the bidirectional
     * relationship.
     *
     * @param categorie The category to be removed from the competition
     */
    public void removeCategorie(Categorie categorie) {
        if (categories != null) {
            categories.remove(categorie);
            categorie.setCompetition(null);
        }
    }
}
