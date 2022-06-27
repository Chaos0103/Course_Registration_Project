package toyproject.courseenrollment.controller.form;

import lombok.Data;
import toyproject.courseenrollment.domain.enumtype.CompletionType;
import toyproject.courseenrollment.domain.enumtype.Language;
import toyproject.courseenrollment.domain.enumtype.Major;

@Data
public class LectureForm {

    private Long lectureId;
    private Long professorId;

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

    private Language language;
    private String room;
    private int midExamRate;
    private int finalExamRate;
    private int taskRate;
    private int attendanceRate;
    private int count;

    private String day;
    private int startTime;
    private int endTime;
}
