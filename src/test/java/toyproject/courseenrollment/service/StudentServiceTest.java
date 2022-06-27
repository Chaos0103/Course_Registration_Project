package toyproject.courseenrollment.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import toyproject.courseenrollment.domain.Student;
import toyproject.courseenrollment.domain.enumtype.Major;
import toyproject.courseenrollment.domain.enumtype.SexType;
import toyproject.courseenrollment.dto.StudentInfoDto;
import toyproject.courseenrollment.exception.JoinException;
import toyproject.courseenrollment.repository.StudentRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class StudentServiceTest {

    @Autowired StudentService studentService;
    @Autowired StudentRepository studentRepository;

    @Test
    @DisplayName("학생등록")
    void joinStudent() {
        Long studentId = studentService.join(new StudentInfoDto(22001123L, "student", "1234", Major.COMPUTER, 1, "2022-01-01", SexType.MALE));

        Student findStudent = studentRepository.findById(studentId).get();

        assertThat(findStudent.getId()).isEqualTo(22001123L);
    }

    @Test
    @DisplayName("중복가입")
    void duplicatedJoin() {
        studentRepository.save(new Student(22001123L, "student", "1234", Major.COMPUTER, 1, "2022-01-01", SexType.MALE));

        JoinException joinException = assertThrows(JoinException.class, () -> {
            studentService.join(new StudentInfoDto(22001123L, "student", "1234", Major.COMPUTER, 1, "2022-01-01", SexType.MALE));
        });
        assertThat(joinException.getMessage()).isEqualTo("이미 등록된 학번입니다.");
    }

    @Test
    @DisplayName("학생삭제")
    void deleteStudent() {
        Student savedStudent = studentRepository.save(new Student(22001123L, "student", "1234", Major.COMPUTER, 1, "2022-01-01", SexType.MALE));

        studentService.delete(savedStudent.getId());
        Optional<Student> findStudent = studentRepository.findById(22001123L);

        assertThat(findStudent).isEmpty();
    }
}