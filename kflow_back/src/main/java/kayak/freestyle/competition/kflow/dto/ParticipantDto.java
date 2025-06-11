package kayak.freestyle.competition.kflow.dto;

import java.util.List;
import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
public class ParticipantDto implements HasId {

    @EqualsAndHashCode.Include
    private Long id;

    @NotBlank
    private String name;

    private int bibNb;

    private String club;

    @JsonIgnore
    private Set<CategorieDto> categories;

    private List<RunDto> runs;

    @PostMapping
    public ResponseEntity<ParticipantDto> save(@RequestBody ParticipantDto participantDto) {
        return ResponseEntity.ok(participantDto);
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
