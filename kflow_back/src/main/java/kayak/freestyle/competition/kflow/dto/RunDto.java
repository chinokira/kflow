package kayak.freestyle.competition.kflow.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import kayak.freestyle.competition.kflow.models.Stage;
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
public class RunDto implements HasId {

    @EqualsAndHashCode.Include
    private Long id;

    private int duration;

    private float score;

    @JsonIgnore
    private Stage stage;
}
