package kayak.freestyle.competition.kflow.models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * Represents a stage within a competition category.
 * A stage is a specific phase of the competition where participants
 * perform their runs according to defined rules and scoring criteria.
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
@EqualsAndHashCode
public class Stage {

    /**
     * Unique identifier for the stage.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The name of the stage (e.g., "Qualification", "Semi-Final", "Final").
     * Cannot be null.
     */
    @Column(nullable = false)
    private String name;

    /**
     * The number of runs allowed per participant in this stage.
     * Cannot be null.
     */
    @Column(nullable = false)
    private Integer nbRun;

    /**
     * The rules and scoring criteria specific to this stage.
     * Cannot be null.
     */
    @Column(nullable = false)
    private String rules;

    /**
     * The category this stage belongs to.
     * Cannot be null.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categorie_id", nullable = false)
    @JsonBackReference("categorie-stages")
    private Categorie categorie;

    /**
     * List of runs performed in this stage.
     * Each run represents a participant's attempt.
     */
    @OneToMany(mappedBy = "stage", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("stage-runs")
    @Builder.Default
    private List<Run> runs = new ArrayList<>();

    /**
     * Adds a run to this stage and establishes the bidirectional relationship.
     *
     * @param run The run to be added to the stage
     */
    public void addRun(Run run) {
        if (runs == null) {
            runs = new ArrayList<>();
        }
        runs.add(run);
        run.setStage(this);
    }

    /**
     * Removes a run from this stage and breaks the bidirectional relationship.
     *
     * @param run The run to be removed from the stage
     */
    public void removeRun(Run run) {
        if (runs != null) {
            runs.remove(run);
            run.setStage(null);
        }
    }
}
