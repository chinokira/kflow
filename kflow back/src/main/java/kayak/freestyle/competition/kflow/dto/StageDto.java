package kayak.freestyle.competition.kflow.dto;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import kayak.freestyle.competition.kflow.models.Categorie;
import kayak.freestyle.competition.kflow.models.Run;
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
public class StageDto implements HasId {

    @EqualsAndHashCode.Include
    private long id;

    @Positive
    private int nbRun;

    @NotBlank
    private String rules;

    private int nbCompetitor;

    @JsonIgnore
    private Categorie categorie;

    @JsonIgnore
    private List<Run> runs;

    @PostMapping
    public ResponseEntity<StageDto> save(@RequestBody StageDto stageDto) {
        return ResponseEntity.ok(stageDto);
    }
}
