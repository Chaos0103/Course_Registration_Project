package toyproject.courseenrollment.repository.custom;

import toyproject.courseenrollment.domain.Lecture;
import toyproject.courseenrollment.domain.enumtype.CompletionType;

import java.util.List;

public interface LectureRepositoryCustom {

    List<Lecture> findSearch(CompletionType type, String lectureName, String professorName);
}
