package com.eb.earbee.business.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BusinessValidationRequest {
    int bNo;
    int zipCode; // 우편번호

}

