package com.eb.earbee.signin.signincontroller;


import com.eb.earbee.signin.entity.SignInEntity;
import com.eb.earbee.signin.service.SignInService;
import com.eb.earbee.signin.dto.SignInDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller("/earbee")
public class SignInController {

    @Autowired
    SignInService signInService;

    @GetMapping("/one")
    public String mainUser(){
        return "signin/one";
    }


    @GetMapping("/signins/new")
    public String createForm() {
        return "signin/signins/createSignInForm";
    }

    @PostMapping("/signins/new")
    public String create(SignInDto form, Model model) {
        SignInEntity signInEntity = form.toEntity();
        if(signInEntity == null){
            model.addAttribute("warningMessage", "SignInEntity is null!");
            return "redirect:/one";
        }

        return "one";
    }



}
