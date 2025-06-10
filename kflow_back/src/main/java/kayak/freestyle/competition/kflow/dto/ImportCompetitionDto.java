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
public class ImportCompetitionDto{

    private String startDate;
    private String endDate;
    private String level;
    private String place;
    private List<CategorieDto> categories;
}
