package kayak.freestyle.competition.kflow.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Setter
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class CompetitionDto implements HasId {

    @EqualsAndHashCode.Include
    private long id;

    @DateTimeFormat(style = "SS")
    private LocalDate startDate;

    @DateTimeFormat(style = "SS")
    private LocalDate endDate;

    private String level;

    private String place;

    private List<CategorieDto> categories = new ArrayList<>();

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }
}
