package kayak.freestyle.competition.kflow.models;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * Represents a participant in the kayak freestyle competition system. A
 * participant is an athlete who competes in one or more categories and has
 * multiple runs during the competition.
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
public class Participant {

    /**
     * Unique identifier for the participant.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    /**
     * The bib number assigned to the participant for the competition. Cannot be
     * null.
     */
    @Column(nullable = false)
    private int bibNb;

    /**
     * The full name of the participant. Cannot be null.
     */
    @Column(nullable = false)
    private String name;

    /**
     * The club or team the participant represents. Can be null if the
     * participant is not affiliated with any club.
     */
    @Column
    private String club;

    /**
     * List of categories the participant is registered in. A participant can
     * compete in multiple categories.
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JsonBackReference("participant-categories")
    @lombok.Builder.Default
    private Set<Categorie> categories = new HashSet<>();

    /**
     * List of runs performed by the participant during the competition. Each
     * run represents a single attempt in a category.
     */
    @OneToMany(mappedBy = "participant", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @lombok.Builder.Default
    private Set<Run> runs = new HashSet<>();

    /**
     * Adds a run to this participant and establishes the bidirectional
     * relationship.
     *
     * @param run The run to be added to the participant
     */
    public void addRun(Run run) {
        if (runs == null) {
            runs = new HashSet<>();
        }
        runs.add(run);
        run.setParticipant(this);
    }

    /**
     * Removes a run from this participant and breaks the bidirectional
     * relationship.
     *
     * @param run The run to be removed from the participant
     */
    public void removeRun(Run run) {
        if (runs != null) {
            runs.remove(run);
            run.setParticipant(null);
        }
    }
}
