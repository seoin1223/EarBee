package com.eb.earbee.business.controller;


import com.eb.earbee.business.dto.BusinessForm;
import com.eb.earbee.business.entity.Business;
import com.eb.earbee.business.service.BusinessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/earbee")
@Slf4j
public class BusinessController {
    @Autowired
    private BusinessService businessService;

    // 업체 등록 게시판
    @GetMapping("")
    public String businessCollection(Model m){
        List<Business> businessList = businessService.findAll();
        m.addAttribute("businessList",businessList);
        
        return "business/main";
    }

    // 업체 등록 작성 페이지
    @GetMapping("/applyplace")
    public String applyPlace(){
        return "business/apply";
    }

    @PostMapping("/business/add")
    public String add(BusinessForm businessForm){
        Business business = businessService.addBusiness(businessForm.toEntity());
        log.info(business.toString());
        return "redirect:/earbee";
    }

}
