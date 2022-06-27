package toyproject.courseenrollment.repository.custom;

import toyproject.courseenrollment.domain.Student;
import toyproject.courseenrollment.domain.enumtype.Major;

import java.util.List;

public interface StudentRepositoryCustom {
    List<Student> findStudents(String name, Major major);
}
