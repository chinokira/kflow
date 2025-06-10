package kayak.freestyle.competition.kflow.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class CategorieDto implements HasId {
    @EqualsAndHashCode.Include
    private Long id;

    @NotBlank
    private String name;

    @JsonIgnore
    private List<ParticipantDto> participants;

    @JsonIgnore
    private List<StageDto> stages;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
