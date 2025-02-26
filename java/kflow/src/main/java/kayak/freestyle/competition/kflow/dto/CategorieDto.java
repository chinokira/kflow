package kayak.freestyle.competition.kflow.dto;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.NotBlank;
import kayak.freestyle.competition.kflow.models.Competition;
import kayak.freestyle.competition.kflow.models.Stage;
import kayak.freestyle.competition.kflow.models.User;
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
public class CategorieDto implements HasId {

    @EqualsAndHashCode.Include
    private long id;

    @NotBlank
    private String name;

    @JsonIgnore
    private List<User> users;

    @JsonIgnore
    private Competition competition;

    @JsonIgnore
    private List<Stage> stages;

    @PostMapping
    public ResponseEntity<CategorieDto> save(@RequestBody CategorieDto categorieDto) {
        return ResponseEntity.ok(categorieDto);
    }
}
