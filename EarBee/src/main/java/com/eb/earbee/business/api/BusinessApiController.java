package com.eb.earbee.business.api;



import com.eb.earbee.business.request.BusinessApplyRequest;

import com.eb.earbee.business.service.BusinessApiService;
import jakarta.xml.ws.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/business")

public class BusinessApiController {

    @Autowired
    private BusinessApiService businessApiService;


    // 정상적으로 변수를 가져오는지 체크
    @GetMapping()
    public List<String> checkValue(){
        return businessApiService.checkValue();
    }


    // 모달창에서 넣은 사업자 번호를 조회
    @PostMapping("/search")
    public <T> Response<T> businessSearchNum(@RequestBody BusinessApplyRequest dto){
        businessApiService.businessSerchNum(dto);
        return null;
    }
}
