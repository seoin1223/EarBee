package com.eb.earbee.business.dto;

import com.eb.earbee.business.entity.Business;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BusinessForm {
    private Long id;
    private String bNo;
    private String title;
    private String zipCode;
    private String addr;
    private String detail;
    private String author;
    private String checked;

    public Business toEntity(){
        return new Business(id,title,bNo,zipCode,addr,detail,"false");
    }


}
