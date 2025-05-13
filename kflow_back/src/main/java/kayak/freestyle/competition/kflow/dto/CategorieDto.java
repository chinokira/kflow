package kayak.freestyle.competition.kflow.dto;

import java.util.List;
import java.util.Set;

import jakarta.validation.constraints.NotBlank;
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

    private Set<ParticipantDto> participants;
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
