package toyproject.courseenrollment.controller.form;

import lombok.Data;
import toyproject.courseenrollment.domain.enumtype.Major;

@Data
public class ProfessorForm {

    private String name;
    private String password;
    private Major major;
    private String email;
    private String office;
}
