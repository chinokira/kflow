package kayak.freestyle.competition.kflow.models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * Represents a competition category in the kayak freestyle competition system.
 * A category is a division within a competition that groups participants based
 * on certain criteria (e.g., age group, skill level).
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
public class Categorie {

    /**
     * Unique identifier for the category.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    /**
     * The name of the category (e.g., "Men's Expert", "Women's Open"). Cannot
     * be null.
     */
    @Column(nullable = false)
    private String name;

    /**
     * The competition this category belongs to. Cannot be null.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "competition_id", nullable = false)
    @JsonBackReference("competition-categories")
    private Competition competition;

    /**
     * List of stages associated with this category. Each stage represents a
     * different phase of the competition.
     */
    @OneToMany(mappedBy = "categorie", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("categorie-stages")
    private List<Stage> stages = new ArrayList<>();

    /**
     * Set of participants registered in this category.
     */
    @ManyToMany(mappedBy = "categories", fetch = FetchType.LAZY)
    @JsonManagedReference("participant-categories")
    private Set<Participant> participants = new HashSet<>();

    /**
     * Adds a stage to this category and establishes the bidirectional
     * relationship.
     *
     * @param stage The stage to be added to the category
     */
    public void addStage(Stage stage) {
        if (stages == null) {
            stages = new ArrayList<>();
        }
        stages.add(stage);
        stage.setCategorie(this);
    }

    /**
     * Removes a stage from this category and breaks the bidirectional
     * relationship.
     *
     * @param stage The stage to be removed from the category
     */
    public void removeStage(Stage stage) {
        if (stages != null) {
            stages.remove(stage);
            stage.setCategorie(null);
        }
    }

    /**
     * Adds a participant to this category and establishes the bidirectional
     * relationship.
     *
     * @param participant The participant to be added to the category
     */
    public void addParticipant(Participant participant) {
        if (participants == null) {
            participants = new HashSet<>();
        }
        participants.add(participant);
        if (participant.getCategories() == null) {
            participant.setCategories(new HashSet<>());
        }
        if (!participant.getCategories().contains(this)) {
            participant.getCategories().add(this);
        }
    }

    /**
     * Removes a participant from this category and breaks the bidirectional
     * relationship.
     *
     * @param participant The participant to be removed from the category
     */
    public void removeParticipant(Participant participant) {
        if (participants != null) {
            participants.remove(participant);
            participant.getCategories().remove(this);
        }
    }
}
