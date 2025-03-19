package kayak.freestyle.competition.kflow.models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

@Entity
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@ToString
@EqualsAndHashCode
public class Participant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int bibNb;

    @Column(nullable = false)
    private String name;

    @Column
    private String club;

    @ManyToMany(mappedBy = "participants")
    @JsonBackReference("categorie-participants")
    private List<Categorie> categories = new ArrayList<>();

    @OneToMany(mappedBy = "participant", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("participant-runs")
    private List<Run> runs = new ArrayList<>();

    public void addRun(Run run) {
        if (runs == null) {
            runs = new ArrayList<>();
        }
        runs.add(run);
        run.setParticipant(this);
    }

    public void removeRun(Run run) {
        if (runs != null) {
            runs.remove(run);
            run.setParticipant(null);
        }
    }
}
