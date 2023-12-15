package com.eb.earbee.business.api;



import com.eb.earbee.business.dto.BusinessApplyDto;

import com.eb.earbee.business.service.BusinessApiService;
import jakarta.xml.ws.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
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
    public <T> Response<T> businessSearchNum(@RequestBody BusinessApplyDto dto){
        businessApiService.businessSErchNum(dto);





        return null;
    }
}
