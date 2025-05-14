package kayak.freestyle.competition.kflow.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@JsonIgnoreProperties(ignoreUnknown = true)
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

    private StageDto stage;

    @JsonIgnore
    private ParticipantDto participant;

    public String getStageName() {
        if (stage != null && stage.getName() != null) {
            return stage.getName();
        }
        return null;
    }
}
