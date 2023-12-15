package com.eb.earbee.business.service;


import com.eb.earbee.business.dto.BusinessApplyDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

@Service
@PropertySource("classpath:business.properties")
public class BusinessApiService {
    @Value("${business.url}")
    private String urlBusiness;
    @Value("${business.encoding}")
    private String encodingBusiness;
    @Value("${business.decoding}")
    private String decodingBusiness;


    // 변수값이 정상적으로 들어오는지 확인하는 메서드

    public List<String> checkValue() {
        return Arrays.asList(urlBusiness,encodingBusiness,decodingBusiness);
    }

    public void businessSErchNum(BusinessApplyDto dto) {
        StringBuilder str = new StringBuilder();
        str.append(urlBusiness);
        str.append(encodingBusiness);

        String body ="";

        try {
            URL url = new URL(str.toString());
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();




        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        
    }
}
