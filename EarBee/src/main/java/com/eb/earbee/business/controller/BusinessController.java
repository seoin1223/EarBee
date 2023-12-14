package com.eb.earbee.business.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/main")
@Slf4j
public class BusinessController {

    // 업체 등록 게시판
    @GetMapping("")
    public String businessCollection(){
        return "business/main";
    }

    // 업체 등록 작성 페이지
    @GetMapping("/insert")
    public String applyPlace(){

        return "apply";
    }

}
