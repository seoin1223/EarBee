package com.eb.earbee.business.service;

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
        String url ="api.odcloud.kr/api" ;
        String encoding = "XNM%2FAHJB%2B68HV79cSGOQ%2BDtMs87Gq3gmewT2NdPV8a2uH1uwWaF2k3L1CjEypfOMtotlgr%2FG%2BYhyNtDU8DxZuw%3D%3D";
        String decoding = "XNM/AHJB+68HV79cSGOQ+DtMs87Gq3gmewT2NdPV8a2uH1uwWaF2k3L1CjEypfOMtotlgr/G+YhyNtDU8DxZuw==";

        List<String> result = businessApiService.checkValue();

        List<String> expected = Arrays.asList(url,encoding,decoding);

        assertEquals(expected.toString(),result.toString(),"checkValue() 실패");

    }

}