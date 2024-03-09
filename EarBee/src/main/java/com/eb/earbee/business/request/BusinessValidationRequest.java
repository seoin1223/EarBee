package com.eb.earbee.business.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BusinessValidationRequest {
    int bNo;
    int zipCode; // 우편번호
    String addr; // 시도명
    String detail; // 전체 도로명 주소
}
