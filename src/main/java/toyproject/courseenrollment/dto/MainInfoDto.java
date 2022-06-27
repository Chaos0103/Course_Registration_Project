package toyproject.courseenrollment.dto;

import lombok.Data;
import toyproject.courseenrollment.domain.enumtype.CompletionType;
import toyproject.courseenrollment.domain.enumtype.Major;

@Data
public class MainInfoDto {

    private int openYear;
    private int semester;

    private Major major;
    private String courseId;
    private String courseClass;
    private String courseTitle;
    private CompletionType completionType;
    private float credit;
    private int theory;
    private int lab;
    private int year;

    public MainInfoDto(int openYear, int semester, Major major, String courseId, String courseClass, String courseTitle, CompletionType completionType,
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
