package kayak.freestyle.competition.kflow.dto;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StageDto implements HasId {

    private Long id;

    @Positive
    private int nbRun;

    @NotBlank
    private String rules;

    private String name;

    @JsonIgnore
    private CategorieDto categorie;

    @JsonIgnore
    private List<RunDto> runs;

    @PostMapping
    public ResponseEntity<StageDto> save(@RequestBody StageDto stageDto) {
        return ResponseEntity.ok(stageDto);
    }
}
