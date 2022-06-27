package toyproject.courseenrollment.domain.valuetype;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import toyproject.courseenrollment.domain.enumtype.CompletionType;
import toyproject.courseenrollment.domain.enumtype.Major;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MainInfo {

    private int openYear;
    private int semester;

    @Enumerated(EnumType.STRING)
    private Major major;
    private String courseId;
    private String courseClass;
    private String courseTitle;
    @Enumerated(EnumType.STRING)
    private CompletionType completionType;
    private float credit;
    private int theory;
    private int lab;
    private int year;

    public MainInfo(int openYear, int semester, Major major, String courseId, String courseClass, String courseTitle, CompletionType completionType,
                    float credit, int theory, int lab, int year) {
        this.openYear = openYear;
        this.semester = semester;
        this.major = major;
        this.courseId = courseId;
        this.courseClass = courseClass;
        this.courseTitle = courseTitle;
        this.completionType = completionType;
        this.credit = credit;
        this.theory = theory;
        this.lab = lab;
        this.year = year;
    }
}
