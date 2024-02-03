package com.eb.earbee.business.dto;

import com.eb.earbee.business.entity.Business;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BusinessForm {
    private Long id;
    private String bNo;
    private String zipCode;
    private String addr;
    private String detail;

    public Business toEntity(){
        return new Business(id,bNo,zipCode,addr,detail);
    }


}
