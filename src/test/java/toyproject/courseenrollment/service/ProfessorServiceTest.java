package toyproject.courseenrollment.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import toyproject.courseenrollment.domain.Professor;
import toyproject.courseenrollment.domain.enumtype.Major;
import toyproject.courseenrollment.dto.ProfessorInfoDto;
import toyproject.courseenrollment.repository.ProfessorRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ProfessorServiceTest {

    @Autowired ProfessorService professorService;
    @Autowired ProfessorRepository professorRepository;

    @Test
    @DisplayName("교수등록")
    void joinProfessor() {
        //given
        Long professorId = professorService.join(new ProfessorInfoDto(null, "교수", "1234", Major.COMPUTER, "abc123@naver.com", "센800"));

        //when
        Professor findProfessor = professorRepository.findById(professorId).get();

        //then
        assertThat(findProfessor.getName()).isEqualTo("교수");
    }

    @Test
    @DisplayName("교수삭제")
    void deleteProfessor() {
        //given
        Professor savedProfessor = professorRepository.save(new Professor("교수", "1234", Major.COMPUTER, "abc123@naver.com", "센800"));

        //when
        professorService.delete(savedProfessor.getId());

        //then
        Optional<Professor> findProfessor = professorRepository.findById(savedProfessor.getId());
        assertThat(findProfessor).isEmpty();
    }
}