package kayak.freestyle.competition.kflow.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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
public class Run {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue
    private long id;

    private int duration;

    private float score;

    @ManyToOne
    private Stage stage;
}
