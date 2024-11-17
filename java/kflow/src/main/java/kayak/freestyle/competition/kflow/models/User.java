package kayak.freestyle.competition.kflow.models;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
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
public class User {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue
    private long id;

    private int bibNb;

    private String name;

    @ManyToMany(mappedBy = "users")
    private List<Categorie> categories;
}
