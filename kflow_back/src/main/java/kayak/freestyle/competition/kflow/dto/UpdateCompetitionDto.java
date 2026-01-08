package kayak.freestyle.competition.kflow.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCompetitionDto implements HasId {
    private Long id;
    private String startDate;
    private String endDate;
    private String level;
    private String place;
    private List<CategorieDto> categories;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
} 