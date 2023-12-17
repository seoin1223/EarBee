package com.eb.earbee.business.service;


import com.eb.earbee.business.request.BusinessApplyRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

@Service
@PropertySource("classpath:business.properties")
public class BusinessApiService {
    @Value("${business.url}")
    private String urlBusiness;
    @Value("${business.encoding}")
    private String encodingKey;
    @Value("${business.decoding}")
    private String decodingKey;


    // 변수값이 정상적으로 들어오는지 확인하는 메서드

    public List<String> checkValue() {
        return Arrays.asList(urlBusiness,encodingKey,decodingKey);
    }

    public void businessSerchNum(BusinessApplyRequest dto) {
        StringBuilder str = new StringBuilder();
        str.append(urlBusiness);
        str.append(encodingKey);

        System.out.println(str.toString());

        try {
            URL url = new URL(str.toString());
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST"); // 전송방식 post
            con.setRequestProperty("Content-Type","application/json; charset = utf-8"); // request body 전송을 aspplcation/json으로 서버 전달
            con.setDoOutput(true); // outputStream으로 post data를 넘김

            OutputStream os = con.getOutputStream(); // Request body에 넣을 data가 담긴 OutputStream
            os.write(dto.getJsonApply().getBytes(StandardCharsets.UTF_8));
            os.flush();
            os.close();

            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8));
            StringBuffer sb = new StringBuffer();
            String inputLine;

            while((inputLine=br.readLine())!=null){
                sb.append(inputLine);
            }
            br.close();

            System.out.println(sb.toString());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
