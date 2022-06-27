package toyproject.courseenrollment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.courseenrollment.domain.Professor;
import toyproject.courseenrollment.domain.Student;
import toyproject.courseenrollment.exception.LoginException;
import toyproject.courseenrollment.repository.ProfessorRepository;
import toyproject.courseenrollment.repository.StudentRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LoginService {

    private final StudentRepository studentRepository;
    private final ProfessorRepository professorRepository;

    public Long studentLogin(Long studentId, String password) {
        Student student = findStudent(studentId);
        if (student.getPassword().equals(password)) {
            return studentId;
        } else {
            throw new LoginException("비밀번호가 틀렸습니다.");
        }
    }

    public Long professorLogin(String email, String password) {
        Professor professor = findProfessor(email);
        if (professor.getPassword().equals(password)) {
            return professor.getId();
        } else {
            throw new LoginException("비밀번호가 틀렸습니다.");
        }
    }

    private Student findStudent(Long studentId) {
        Optional<Student> findStudent = studentRepository.findById(studentId);
        if (findStudent.isEmpty()) {
            throw new LoginException("등록되지 않은 학생입니다.");
        }
        return findStudent.get();
    }

    private Professor findProfessor(String email) {
        Optional<Professor> findProfessor = professorRepository.findLogin(email);
        if (findProfessor.isEmpty()) {
            throw new LoginException("등록되지 않은 교수입니다.");
        }
        return findProfessor.get();
    }
}
