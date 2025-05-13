package kayak.freestyle.competition.kflow.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class Competition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
    private String place;

    @Column(nullable = false)
    private String level;

    @OneToMany(mappedBy = "competition", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("competition-categories")
    @Builder.Default
    @Column(nullable = true)
    private List<Categorie> categories = new ArrayList<>();

    public void addCategorie(Categorie categorie) {
        if (categories == null) {
            categories = new ArrayList<>();
        }
        categories.add(categorie);
        categorie.setCompetition(this);
    }

    public void removeCategorie(Categorie categorie) {
        if (categories != null) {
            categories.remove(categorie);
            categorie.setCompetition(null);
        }
    }
}
