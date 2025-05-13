package kayak.freestyle.competition.kflow.dto;

import java.util.List;
import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.constraints.NotBlank;
import kayak.freestyle.competition.kflow.models.Competition;
import kayak.freestyle.competition.kflow.models.Stage;
import kayak.freestyle.competition.kflow.models.Participant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategorieDto implements HasId {

    private Long id;

    @NotBlank
    private String name;

    private Set<Participant> participants;

    private Competition competition;

    private List<Stage> stages;

    @PostMapping
    public ResponseEntity<CategorieDto> save(@RequestBody CategorieDto categorieDto) {
        return ResponseEntity.ok(categorieDto);
    }
}
