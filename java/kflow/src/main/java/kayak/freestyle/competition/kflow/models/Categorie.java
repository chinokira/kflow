package kayak.freestyle.competition.kflow.models;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
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

@NoArgsConstructor
@SuperBuilder
@Setter
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Entity
public class Categorie {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue
    private long id;

    private String name;
    
    @ManyToMany(mappedBy = "categories")
    private List<User> users;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "competition_id")
    private Competition competition;

    @OneToMany(mappedBy = "categorie", cascade=CascadeType.ALL, orphanRemoval=true, fetch=FetchType.LAZY)
    private List<Stage> stages;

}
