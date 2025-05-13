package kayak.freestyle.competition.kflow.dto;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import kayak.freestyle.competition.kflow.models.Categorie;
import kayak.freestyle.competition.kflow.models.Run;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParticipantDto implements HasId {

    private Long id;

    @NotBlank
    private String name;

    private int bibNb;

    private String club;

    @JsonIgnore
    private List<Categorie> categories;

    @JsonIgnore
    private List<Run> runs;

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
