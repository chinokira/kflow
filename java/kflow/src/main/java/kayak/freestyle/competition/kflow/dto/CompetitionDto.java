package kayak.freestyle.competition.kflow.dto;

import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.constraints.NotBlank;
import kayak.freestyle.competition.kflow.models.Categorie;
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
public class CompetitionDto implements HasId{

    @EqualsAndHashCode.Include
    private long id;

    @NotBlank
    @DateTimeFormat(style="SS")
    private String date;

    @NotBlank
    private String level;

    @NotBlank
    private String place;

    private List<Categorie> categories;

    @PostMapping
    public ResponseEntity<CompetitionDto> save(@RequestBody CompetitionDto competitionDto) {
        return ResponseEntity.ok(competitionDto);
    }
}
