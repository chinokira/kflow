package kayak.freestyle.competition.kflow.dto;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.constraints.NotBlank;
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

    @NotBlank
    private int nbRun;

    @NotBlank
    private String rules;

    @NotBlank
    private int nbCompetitor;

    private Long categorieId;

    private Long[] runsId;

    @PostMapping
    public ResponseEntity<StageDto> save(@RequestBody StageDto stageDto) {
        return ResponseEntity.ok(stageDto);
    }
}
