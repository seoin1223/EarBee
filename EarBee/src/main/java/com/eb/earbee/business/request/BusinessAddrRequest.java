package com.eb.earbee.business.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;

import java.util.Collections;
import java.util.List;

@Data
public class BusinessAddrRequest {
    String keyword;
    int currentPage; // 현재 페이지


    public BusinessAddrRequest(String keyword, int currentPage) {
        if(currentPage == 0){
            currentPage = 1;
        }
        this.keyword = keyword;
        this.currentPage = currentPage;

    }
}
