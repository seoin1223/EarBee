package testController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {
    @GetMapping("/")
    public String tempJsp() {
        // prefix: /WEB-INF/views
        // suffix: .jsp
        // 풀경로: /WEB-INF/views/index.jsp
        return "index";
    }


}
