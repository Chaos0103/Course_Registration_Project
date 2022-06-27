package toyproject.courseenrollment.controller.form;

import lombok.Data;
import toyproject.courseenrollment.domain.enumtype.CompletionType;
import toyproject.courseenrollment.domain.enumtype.Major;

@Data
public class LectureSearch {

    private CompletionType completionType;
    private Major major;
    private String lectureName;
    private String professorName;

}
