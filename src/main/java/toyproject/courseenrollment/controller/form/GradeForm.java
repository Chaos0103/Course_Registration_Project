package toyproject.courseenrollment.controller.form;

import lombok.Data;
import toyproject.courseenrollment.domain.enumtype.GradeType;

@Data
public class GradeForm {

    private Long gradeId;
    private GradeType score;
    private float midExam;
    private float finalExam;
    private float task;
    private float attendance;
}
