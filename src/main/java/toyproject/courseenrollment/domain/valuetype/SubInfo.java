package toyproject.courseenrollment.domain.valuetype;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import toyproject.courseenrollment.domain.enumtype.Language;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SubInfo {

    @Enumerated(EnumType.STRING)
    private Language language;
    private String room;

    private int midExamRate;
    private int finalExamRate;
    private int taskRate;
    private int attendanceRate;
    private int count;

    public SubInfo(Language language, String room
                   ,int midExamRate, int finalExamRate, int taskRate, int attendanceRate, int count) {
        this.language = language;
        this.room = room;
        this.midExamRate = midExamRate;
        this.finalExamRate = finalExamRate;
        this.taskRate = taskRate;
        this.attendanceRate = attendanceRate;
        this.count = count;
    }
}
