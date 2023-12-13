package com.eb.earbee.business.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/main")
@Slf4j
public class BusinessController {

    @GetMapping("")
    public String businessCollection(){

        return "business/main";
    }

    @GetMapping("/insert")
    public String insertBusiness(){

        return "business/insert";
    }

}
