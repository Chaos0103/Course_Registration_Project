package toyproject.courseenrollment.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "/home";
    }

//    @GetMapping("/student")
    public String studentHome() {
        return "/studentHome";
    }

//    @GetMapping("/professor")
    public String professorHome() {
        return "/professorHome";
    }
}
