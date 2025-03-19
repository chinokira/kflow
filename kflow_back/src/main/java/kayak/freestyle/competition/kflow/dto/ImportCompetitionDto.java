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
public class ImportCompetitionDto {

    private String startDate;
    private String endDate;
    private String level;
    private String place;
    private List<ImportCategoryDto> categories;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ImportCategoryDto {

        private String name;
        private List<ImportParticipantDto> participants;
        private List<ImportStageDto> stages;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ImportParticipantDto {

        private int bibNb;
        private String name;
        private String club;
        private List<ImportRunDto> runs;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ImportStageDto {

        private int nbRun;
        private String rules;
        private String name;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ImportRunDto {

        private int duration;
        private double score;
        private String stageName;
    }
}
