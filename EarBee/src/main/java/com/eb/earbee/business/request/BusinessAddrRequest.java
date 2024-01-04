package com.eb.earbee.business.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;

import java.util.Collections;
import java.util.List;

@Data
@AllArgsConstructor
public class BusinessAddrRequest {
    String keyword;

    int currentPage;


}
