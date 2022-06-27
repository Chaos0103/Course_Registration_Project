package toyproject.courseenrollment.repository.custom;

import toyproject.courseenrollment.domain.Professor;
import toyproject.courseenrollment.domain.enumtype.Major;

import java.util.List;

public interface ProfessorRepositoryCustom {

    List<Professor> findProfessors(String name, Major major);
}
