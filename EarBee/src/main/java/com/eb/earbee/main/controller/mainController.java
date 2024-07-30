package com.eb.earbee.main.controller;



import com.eb.earbee.main.dto.UserDto;
import com.eb.earbee.main.entity.User;
import com.eb.earbee.main.service.UserService;
import com.eb.earbee.security.entity.TemporaryUser;
import com.eb.earbee.security.login.basicLogin.PrincipalUserDetails;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.hibernate.Session;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class mainController {

    private UserService userService;

    public mainController(UserService userService) {
        this.userService = userService;

    }

    @GetMapping
    public String index(Authentication authentication, Model m){

        if(authentication != null && authentication.isAuthenticated()) {

            PrincipalUserDetails principalUserDetails = (PrincipalUserDetails) authentication.getPrincipal();
            User user = principalUserDetails.getUser();

            if (user != null) {
                m.addAttribute("id", user.getId());
                System.out.println(user.toString());
            }
        }else {
            System.out.println("User is not logged in.");
        }

        return "main/main"; // Mustache 템플릿 이름
    }

    @GetMapping("/join")
    public String joinForm(Model model){
        return "user/joinForm";
    }

    @GetMapping("/joinOauth2")
    public String joinOauth2(HttpServletRequest request, Model model){
        HttpSession session = request.getSession(false);

        if(session != null) {
            PrincipalUserDetails principal = (PrincipalUserDetails) session.getAttribute("principal");
            if(principal != null) {
                model.addAttribute("user", principal.getTemporaryUser());
                return "/user/joinOauth2Form";
            }
        }
        return "/main/main";
    }

    @GetMapping("/login")
    public String login(Model model){
        return "user/login";
    }

    @PostMapping("/join")
    public String join(HttpServletRequest request, UserDto user){
        System.out.println("join"+user);
        HttpSession session = request.getSession();
        PrincipalUserDetails principal = (PrincipalUserDetails) session.getAttribute("principal");
        boolean check = principal.isTemporaryUser();

        if(check) {
            session.removeAttribute("principal");

        }
        User resultUser = userService.addUser(user,check);
        System.out.println(resultUser);
        return "redirect:/";

    }

    @GetMapping("/myPage")
    public String myPage(Model model){
        return "main/myPage";

    }


}
