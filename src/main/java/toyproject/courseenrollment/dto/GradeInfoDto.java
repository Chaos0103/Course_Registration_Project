package toyproject.courseenrollment.dto;

import lombok.Data;
import toyproject.courseenrollment.domain.Grade;
import toyproject.courseenrollment.domain.enumtype.GradeType;

@Data
public class GradeInfoDto {

    private Long gradeId;
    private Long courseId;
    private GradeType score;
    private float midExam;
    private float finalExam;
    private float task;
    private float attendance;

    public GradeInfoDto() {
    }

    public GradeInfoDto(Grade grade) {
        this.score = grade.getScore();
        this.gradeId = grade.getId();
        this.score = grade.getScore();
        this.midExam = grade.getMidExam();
        this.finalExam = grade.getFinalExam();
        this.task = grade.getTask();
        this.attendance = grade.getAttendance();
    }

    public GradeInfoDto(Long gradeId, GradeType score, float midExam, float finalExam, float task, float attendance) {
        this.gradeId = gradeId;
        this.score = score;
        this.midExam = midExam;
        this.finalExam = finalExam;
        this.task = task;
        this.attendance = attendance;
    }


}
