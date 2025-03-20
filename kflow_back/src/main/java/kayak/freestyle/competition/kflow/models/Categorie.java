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
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@ToString
@EqualsAndHashCode
public class Categorie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "competition_id", nullable = false)
    @JsonBackReference("competition-categories")
    private Competition competition;

    @OneToMany(mappedBy = "categorie", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("categorie-stages")
    @Builder.Default
    private List<Stage> stages = new ArrayList<>();

    @ManyToMany(mappedBy = "categories", fetch = FetchType.LAZY)
    @JsonManagedReference("participant-categories")
    @Builder.Default
    private List<Participant> participants = new ArrayList<>();

    public void addStage(Stage stage) {
        if (stages == null) {
            stages = new ArrayList<>();
        }
        stages.add(stage);
        stage.setCategorie(this);
    }

    public void removeStage(Stage stage) {
        if (stages != null) {
            stages.remove(stage);
            stage.setCategorie(null);
        }
    }

    public void addParticipant(Participant participant) {
        if (participants == null) {
            participants = new ArrayList<>();
        }
        participants.add(participant);
        if (!participant.getCategories().contains(this)) {
            participant.getCategories().add(this);
        }
    }

    public void removeParticipant(Participant participant) {
        if (participants != null) {
            participants.remove(participant);
            participant.getCategories().remove(this);
        }
    }
}
