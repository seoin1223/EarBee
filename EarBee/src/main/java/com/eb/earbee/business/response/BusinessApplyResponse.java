package com.eb.earbee.business.response;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BusinessApplyResponse {
    int b_no; // 사업자 번호
    int b_stt_cd; // 정상 사업가 여부 1 : 정상 , 2: 휴업자, 3: 페업자


}
