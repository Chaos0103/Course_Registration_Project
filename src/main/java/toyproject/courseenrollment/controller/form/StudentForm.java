package toyproject.courseenrollment.controller.form;

import lombok.Data;
import toyproject.courseenrollment.domain.enumtype.Major;
import toyproject.courseenrollment.domain.enumtype.SexType;

@Data
public class StudentForm {

    private Long studentId;
    private String name;
    private String password;
    private int year;
    private String birth;

    private SexType sex;
    private Major major;
}
