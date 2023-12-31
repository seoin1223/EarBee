package com.eb.earbee.business.api;



import com.eb.earbee.business.request.BusinessAddrRequest;
import com.eb.earbee.business.request.BusinessApplyRequest;

import com.eb.earbee.business.response.BusinessAddrResponse;
import com.eb.earbee.business.response.BusinessApplyResponse;
import com.eb.earbee.business.service.BusinessApiService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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
    public ResponseEntity<BusinessApplyResponse> businessSearchNum(@RequestBody BusinessApplyRequest dto){
        BusinessApplyResponse result = businessApiService.businessSearchNum(dto);
        return (result !=null)? ResponseEntity.ok(result) : ResponseEntity.badRequest().build();
    }

    // 모달창에서 넣은 주소를 검색
    @PostMapping("/addr")
    public ResponseEntity<BusinessAddrResponse[]> addrSearch(@RequestBody BusinessAddrRequest dto) {
        BusinessAddrResponse[] result = businessApiService.searchAddr(dto);
        System.out.println(Arrays.toString(result));
        return (result !=null)? ResponseEntity.ok(result):ResponseEntity.badRequest().build();
    }
}
