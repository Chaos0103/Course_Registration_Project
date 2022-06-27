package toyproject.courseenrollment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.courseenrollment.domain.Student;
import toyproject.courseenrollment.domain.enumtype.Major;
import toyproject.courseenrollment.dto.StudentInfoDto;
import toyproject.courseenrollment.exception.JoinException;
import toyproject.courseenrollment.repository.StudentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    /**
     * 학생등록
     */
    @Transactional
    public Long join(StudentInfoDto infoDto) {
        duplicatedIdCheck(infoDto.getId());
        Student savedStudent = studentRepository.save(new Student(infoDto.getId(), infoDto.getName(), infoDto.getPassword(), infoDto.getMajor(), infoDto.getYear(), infoDto.getBirth(), infoDto.getSex()));
        return savedStudent.getId();
    }

    /**
     * 학생삭제
     */
    @Transactional
    public void delete(Long id) {
        studentRepository.deleteById(id);
    }

    /**
     * 학생조회
     */
    public List<StudentInfoDto> findStudents(String name, Major major) {
        List<StudentInfoDto> result = new ArrayList<>();
        List<Student> students = studentRepository.findStudents(name, major);
        for (Student student : students) {
            result.add(new StudentInfoDto(student.getId(), student.getName(), student.getPassword(), student.getMajor(), student.getYear(), student.getBirth(), student.getSex()));
        }
        return result;
    }

    /**
     * 중복체크
     */
    private void duplicatedIdCheck(Long id) {
        Optional<Student> findStudent = studentRepository.findById(id);
        if (findStudent.isPresent()) {
            throw new JoinException("이미 등록된 학번입니다.");
        }
    }

}
