package toyproject.courseenrollment.controller.form;

import lombok.Data;
import toyproject.courseenrollment.domain.enumtype.Major;

@Data
public class SearchForm {

    private String studentName;
    private String professorName;

    private Major major;
}
