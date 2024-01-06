package com.eb.earbee.signin.signincontroller;


import com.eb.earbee.signin.service.SignInService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class SignInController {

    private final SignInService signInService;

    @Autowired
    public SignInController(SignInService signInService) {
        this.signInService = signInService;
    }

}
