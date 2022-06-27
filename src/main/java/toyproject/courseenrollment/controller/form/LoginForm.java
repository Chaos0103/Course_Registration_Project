package toyproject.courseenrollment.controller.form;

import lombok.Data;

@Data
public class LoginForm {

    private Long studentId;
    private String email;
    private String password;

}
