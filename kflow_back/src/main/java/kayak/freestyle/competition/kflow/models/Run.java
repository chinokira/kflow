package kayak.freestyle.competition.kflow.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * Represents a single run performed by a participant during a competition
 * stage. A run is a timed attempt where the participant performs freestyle
 * kayak tricks and receives a score based on their performance.
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
public class Run {

    /**
     * Unique identifier for the run.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    /**
     * The duration of the run in seconds. Cannot be null.
     */
    @Column(nullable = false)
    private Integer duration;

    /**
     * The score awarded for this run. Cannot be null.
     */
    @Column(nullable = false)
    private Float score;

    /**
     * The stage in which this run was performed. Cannot be null.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stage_id", nullable = false)
    @JsonBackReference("stage-runs")
    private Stage stage;

    /**
     * The participant who performed this run. Cannot be null.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "participant_id", nullable = false)
    @JsonIgnore
    private Participant participant;

    /**
     * Gets the name of the stage associated with this run. This method is used
     * for JSON serialization.
     *
     * @return The name of the stage, or null if no stage is associated
     */
    @JsonGetter("stageName")
    public String getStageName() {
        return stage != null ? stage.getName() : null;
    }
}
