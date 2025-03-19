package kayak.freestyle.competition.kflow.dto;

import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
public class CompetitionDto implements HasId {

    @EqualsAndHashCode.Include
    private long id;

    @DateTimeFormat(style = "SS")
    private String startDate;

    @DateTimeFormat(style = "SS")
    private String endDate;

    private String level;

    private String place;

    @JsonIgnore
    private List<Categorie> categories;

    @PostMapping
    public ResponseEntity<CompetitionDto> save(@RequestBody CompetitionDto competitionDto) {
        return ResponseEntity.ok(competitionDto);
    }
}
