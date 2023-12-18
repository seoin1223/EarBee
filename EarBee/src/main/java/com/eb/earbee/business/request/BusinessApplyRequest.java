package com.eb.earbee.business.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.minidev.json.JSONObject;


import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusinessApplyRequest {
    String b_no;


    public String getJsonApply(){
        JSONObject json=new JSONObject();
        List<String> str = Collections.singletonList(b_no);
        json.put("b_no",str);
    return json.toJSONString();
    }
}
