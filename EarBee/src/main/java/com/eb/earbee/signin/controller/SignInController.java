package com.eb.earbee.signin.controller;


import com.eb.earbee.signin.entity.SignInEntity;
import com.eb.earbee.signin.service.SignInService;
import com.eb.earbee.signin.dto.SignInDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/login")
public class SignInController {

    @Autowired
    SignInService signInService;

    @GetMapping("asdasdasd")
    public String login() {
        return "redirect:/login/signin/one";
    }

    @GetMapping("/signin/one")
    public String mainUser(){
        return "signin/one";
    }

    @PostMapping("/signins/new")
    public String create(SignInDto form, Model model) {
        SignInEntity signInEntity = form.toEntity();
        if(signInEntity == null){
            model.addAttribute("warningMessage", "SignInEntity is null!");
            System.out.println("안녕 체크");
            return "redirect:/login/signin/one";
        }

        return "one";
    }


    @GetMapping("/signins/new")
    public String createForm() {
        return "signin/signins/createSignInForm";
    }




}
