package com.eb.earbee.business.api;


import com.eb.earbee.business.entity.Business;
import com.eb.earbee.business.dto.BusinessApplyDto;
import jakarta.xml.ws.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api")
@PropertySource("classpath:business.properties")
public class BusinessApiController {

    @Value("${business.url}")
    private String url;

    @Value("${business.encoding}")
    private String encoding;
    @Value("${business.decoding}")
    private String decoding;


    // 정상적으로 변수를 가져오는지 체크
    @GetMapping()
    public List<String> checkValue(){
        return Arrays.asList(url,encoding,decoding);
    }


    // 모달창에서 넣은 사업자 번호를 조회
    @PostMapping("/search")
    public Response<Business> businessSearch(@RequestBody BusinessApplyDto businessApplyDto){
        String url = "";







        return null;
    }
}
