package com.eb.earbee.business.api;



import com.eb.earbee.business.dto.BusinessApplyDto;

import jakarta.xml.ws.Response;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/business")
@PropertySource("classpath:business.properties")
public class BusinessApiController {

    @Value("${business.url}")
    private String urlBusiness;

    @Value("${business.encoding}")
    private String encodingBusiness;
    @Value("${business.decoding}")
    private String decodingBusiness;


    // 정상적으로 변수를 가져오는지 체크
    @GetMapping()
    public List<String> checkValue(){
        return Arrays.asList(urlBusiness,encodingBusiness,decodingBusiness);
    }


    // 모달창에서 넣은 사업자 번호를 조회
    @PostMapping("/search")
    public <T> Response<T> businessSearch(@RequestBody BusinessApplyDto businessApplyDto){
        String url = this.urlBusiness;
        System.out.println(businessApplyDto.toString());

        return null;
    }
}
