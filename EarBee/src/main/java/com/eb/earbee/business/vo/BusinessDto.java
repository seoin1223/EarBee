package com.eb.earbee.business.vo;

import com.eb.earbee.business.entity.Business;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BusinessDto {
    private Long id;

    public Business toEntity(){
        return new Business();
    }

}
