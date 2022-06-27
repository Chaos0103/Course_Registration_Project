package toyproject.courseenrollment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.courseenrollment.domain.Professor;
import toyproject.courseenrollment.domain.enumtype.Major;
import toyproject.courseenrollment.dto.ProfessorInfoDto;
import toyproject.courseenrollment.repository.ProfessorRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProfessorService {

    private final ProfessorRepository professorRepository;

    /**
     * 교수등록
     */
    @Transactional
    public Long join(ProfessorInfoDto infoDto) {
        Professor savedProfessor = professorRepository.save(new Professor(infoDto.getName(), infoDto.getPassword(), infoDto.getMajor(), infoDto.getEmail(), infoDto.getOffice()));
        return savedProfessor.getId();
    }

    /**
     * 교수삭제
     */
    @Transactional
    public void delete(Long id) {
        professorRepository.deleteById(id);
    }

    /**
     * 교수전체조회
     */
    public List<ProfessorInfoDto> findProfessors(String name, Major major) {
        List<ProfessorInfoDto> result = new ArrayList<>();
        List<Professor> professors = professorRepository.findProfessors(name, major);
        for (Professor professor : professors) {
            result.add(new ProfessorInfoDto(professor.getId(), professor.getName(), professor.getPassword(), professor.getMajor(), professor.getEmail(), professor.getOffice()));
        }
        return result;
    }

}
