package com.eb.earbee.main.controller;



import com.eb.earbee.main.dto.UserDto;
import com.eb.earbee.main.entity.User;
import com.eb.earbee.main.service.UserService;
import com.eb.earbee.security.login.basicLogin.PrincipalUserDetails;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class mainController {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private UserService userService;



    public mainController(UserService userService, AuthenticationManagerBuilder authenticationManagerBuilder) {
        this.userService = userService;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }

    @GetMapping
    public String index(Authentication authentication, Model m){

        if(authentication != null && authentication.isAuthenticated()) {

            PrincipalUserDetails principalUserDetails = (PrincipalUserDetails) authentication.getPrincipal();
            User user = principalUserDetails.getUser();

            if (user != null) {
                m.addAttribute("id", user.getId());
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
        return "main/main";
    }


    @PostMapping("/join")
    public String join(HttpServletRequest request, UserDto user){
        boolean check = false;
        HttpSession session = request.getSession();
        PrincipalUserDetails principal = (PrincipalUserDetails) session.getAttribute("principal");

        if(principal != null) {
            check = principal.isTemporaryUser();
            if(check) {
                session.removeAttribute("principal");
            }
        }
        User resultUser = userService.addUser(user,check);

        return "redirect:/";
    }

    // 마이 페이지
    @GetMapping("/myPage")
    public String myPage(Model model, Authentication authed) throws JsonProcessingException {
        return getString(model, authed);
    }

    @GetMapping("/login")
    public String login(Model model){
         return "user/login";
    }


    @PostMapping("/user/update")
    public String updateUser(@ModelAttribute UserDto dao, Model model, Authentication authen) throws JsonProcessingException {
        User resultuser = userService.updateUser(dao.toEntity());
        if(resultuser == null){
            return "redirect:/";
        }
        return "redirect:/myPage";

    }

    private String getString(Model model, Authentication authen) throws JsonProcessingException {
        if(authen != null){
            PrincipalUserDetails principal = (PrincipalUserDetails) authen.getPrincipal();
            if(principal != null){
                ObjectMapper mapper = new ObjectMapper();
                String user = mapper.writeValueAsString(principal.getUser().toFilerUser());
                model.addAttribute("id", principal.getId());
                model.addAttribute("user",user);
            }
        }
        return "user/myPage";
    }


}

