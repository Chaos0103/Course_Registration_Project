package toyproject.courseenrollment.dto;

import lombok.Data;
import toyproject.courseenrollment.domain.enumtype.Language;

@Data
public class SubInfoDto {

    private Language language;
    private String room;
    private int midExamRate;
    private int finalExamRate;
    private int taskRate;
    private int attendanceRate;
    private int count;

    public SubInfoDto(Language language, String room, int midExamRate, int finalExamRate, int taskRate, int attendanceRate, int count) {
        this.language = language;
        this.room = room;
        this.midExamRate = midExamRate;
        this.finalExamRate = finalExamRate;
        this.taskRate = taskRate;
        this.attendanceRate = attendanceRate;
        this.count = count;
    }
}
