package toyproject.courseenrollment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import toyproject.courseenrollment.domain.*;
import toyproject.courseenrollment.domain.enumtype.CompletionType;
import toyproject.courseenrollment.domain.enumtype.Language;
import toyproject.courseenrollment.domain.enumtype.Major;
import toyproject.courseenrollment.domain.enumtype.SexType;
import toyproject.courseenrollment.domain.valuetype.LectureTime;
import toyproject.courseenrollment.domain.valuetype.MainInfo;
import toyproject.courseenrollment.domain.valuetype.SubInfo;
import toyproject.courseenrollment.repository.CourseRepository;
import toyproject.courseenrollment.repository.LectureRepository;
import toyproject.courseenrollment.repository.ProfessorRepository;
import toyproject.courseenrollment.repository.StudentRepository;
import toyproject.courseenrollment.service.CourseService;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    private void init() {
        initService.dbInit1();
        initService.dbInit2();
    }


    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final StudentRepository studentRepository;
        private final ProfessorRepository professorRepository;
        private final LectureRepository lectureRepository;
        private final CourseRepository courseRepository;

        public void dbInit1() {
            Student student = studentRepository.save(new Student(22001001L, "김민정", "alswjd!", Major.COMPUTER, 1, "2001-01-01", SexType.FEMALE));

            Professor professor = professorRepository.save(new Professor("김도년", "ehsus!", Major.COMPUTER, "dnkim@sejong.ac.kr", "센801"));

            MainInfo mainInfo1 = new MainInfo(2022, 1, Major.COMPUTER, "009912", "006", "C프로그래밍및실습",
                    CompletionType.MajorRequirements, 3.0f, 2, 2, 1);
            SubInfo subInfo1 = new SubInfo(Language.KOREAN, "센B206", 50, 20, 20, 10, 40);
            LectureTime lectureTime1 = new LectureTime("목", 1630, 1830);

            MainInfo mainInfo2 = new MainInfo(2022, 1, Major.COMPUTER, "009913", "001", "고급C프로그래밍및실습", CompletionType.MajorRequirements, 3.0f, 2, 2, 1);
            SubInfo subInfo2 = new SubInfo(Language.KOREAN, "센B203", 40, 20, 30, 10, 40);
            LectureTime lectureTime2 = new LectureTime("수", 1630, 1830);

            Lecture lecture1 = lectureRepository.save(new Lecture(professor, mainInfo1, subInfo1, lectureTime1));
            Lecture lecture2 = lectureRepository.save(new Lecture(professor, mainInfo2, subInfo2, lectureTime2));

            Course course = courseRepository.save(new Course(student, lecture1, new Grade()));
        }

        public void dbInit2() {
            Student student = new Student(21001001L, "유지민", "wlals!", Major.SOFTWARE, 2, "2000-04-11", SexType.FEMALE);
            studentRepository.save(student);

            Professor professor = new Professor("박상일", "tkddlf!", Major.SOFTWARE, "park@sejong.ac.kr", "센811");
            professorRepository.save(professor);

            MainInfo mainInfo = new MainInfo(2022, 1, Major.SOFTWARE, "009992", "001", "문제해결및실습:C++", CompletionType.MajorRequirements, 3.0f, 3, 0, 2);
            SubInfo subInfo = new SubInfo(Language.KOREAN, "센B112", 30, 30, 30, 10, 40);
            LectureTime lectureTime = new LectureTime("화", 1200, 1330);
            Lecture lecture = new Lecture(professor, mainInfo, subInfo, lectureTime);
            lectureRepository.save(lecture);

        }

    }

}
