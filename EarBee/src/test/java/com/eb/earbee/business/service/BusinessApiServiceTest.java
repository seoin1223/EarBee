package com.eb.earbee.business.service;

import com.eb.earbee.business.dto.BusinessDto;
import com.eb.earbee.business.request.BusinessApplyRequest;
import com.eb.earbee.business.response.BusinessApplyResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BusinessApiServiceTest {

    @Autowired private BusinessApiService businessApiService;
    @Test
    @DisplayName("properties 변수 가져오기 테스트")
    void checkValue() {
        String url ="https://api.odcloud.kr/api/nts-businessman/v1/status?serviceKey=" ;
        String encoding = "XNM%2FAHJB%2B68HV79cSGOQ%2BDtMs87Gq3gmewT2NdPV8a2uH1uwWaF2k3L1CjEypfOMtotlgr%2FG%2BYhyNtDU8DxZuw%3D%3D";
        String decoding = "XNM/AHJB+68HV79cSGOQ+DtMs87Gq3gmewT2NdPV8a2uH1uwWaF2k3L1CjEypfOMtotlgr/G+YhyNtDU8DxZuw==";

        List<String> result = businessApiService.checkValue();

        List<String> expected = Arrays.asList(url,encoding,decoding);

        assertEquals(expected.toString(),result.toString(),"checkValue() 실패");

    }

    @Test
    @DisplayName("사업자 번호 조회")
    void businessSerchNum() {
        {
            // 사업자 번호 없는 경우
            // Data
            BusinessApplyRequest dto = new BusinessApplyRequest("1234567890");

            // 실제
            BusinessApplyResponse result = businessApiService.businessSearchNum(dto);

            // 예상
            BusinessApplyResponse expected = null;
            // 비교
            assertEquals(expected,result, "사업자 번호 조회 번호 없는 경우가 실패했습니다");
        }

        // 정상적인 사업자 번호일 경우
        {
            // 사업자 번호 있는 경우
            // Data
            BusinessApplyRequest dto = new BusinessApplyRequest("1308189095");

            // 실제
            BusinessApplyResponse result = businessApiService.businessSearchNum(dto);

            // 예상
            BusinessApplyResponse expected = new BusinessApplyResponse(1308189095,1);

            // 비교
            assertEquals(expected,result,"사업자 번호 조회 번호 있는 경우가 실패했습니다");
        }
    }
}