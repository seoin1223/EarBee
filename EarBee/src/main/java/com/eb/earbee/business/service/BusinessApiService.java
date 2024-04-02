package com.eb.earbee.business.service;


import com.eb.earbee.business.entity.Business;
import com.eb.earbee.business.repository.BusinessRepository;
import com.eb.earbee.business.request.BusinessAddrRequest;
import com.eb.earbee.business.request.BusinessApplyRequest;

import com.eb.earbee.business.request.BusinessValidationRequest;
import com.eb.earbee.business.response.BusinessAddrResponse;
import com.eb.earbee.business.response.BusinessApplyResponse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.net.HttpURLConnection;

import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Service
@PropertySource("classpath:business.properties")

public class BusinessApiService {

    @Autowired
    BusinessRepository businessRepository;


    @Value("${business.url}")
    private String urlBusiness;

    @Value("${business.encoding}")
    private String encodingKey;

    @Value("${business.decoding}")
    private String decodingKey;

    @Value("${address.key}")
    private String confmKey;

    @Value("${address.url}")
    private String urlAddr;


    // 변수값이 정상적으로 들어오는지 확인하는 메서드
    public List<String> checkValue() {
        return Arrays.asList(urlBusiness, encodingKey, decodingKey);
    }

    // 사업자 번호 조회 메서드
    @Transactional
    public BusinessApplyResponse businessSearchNum(BusinessApplyRequest dto) {
        try {
            URL url = new URL(urlBusiness+encodingKey);
            HttpURLConnection con = openHttpURLConnection(url);

            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            con.setDoOutput(true);

            writePostData(con, dto.getJsonApply());

            if (con.getResponseCode() != 200) {
                return null;
            }

            String result = readHttpResponse(con);
            JsonNode rootNode = parseJson(result);

            Object matchCnt = rootNode.get("match_cnt");

            if (matchCnt != null) {
                JsonNode dataNode = rootNode.get("data").get(0);
                int bNo = dataNode.get("b_no").asInt();
                int bSttCd = dataNode.get("b_stt_cd").asInt();

                return new BusinessApplyResponse(bNo, bSttCd);
            }

            System.out.println("조회 결과 없음");
            return null;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    // 주소 검색 메서드
    @Transactional
    public BusinessAddrResponse[] searchAddr(BusinessAddrRequest dto) {
        try {
            String encodedKeyword = URLEncoder.encode(dto.getKeyword(), StandardCharsets.UTF_8);

            if (encodedKeyword == null || encodedKeyword.trim().isEmpty()) {
                System.out.println("검색어가 입력되지 않았습니다.");
                return null;
            }
            System.out.println("현재 페이지 " +dto.getCurrentPage());

            String postData = String.format("confmKey=%s&currentPage="+dto.getCurrentPage()+"&keyword=%s&countPerPage=10&resultType=json",
                    URLEncoder.encode(confmKey, StandardCharsets.UTF_8), encodedKeyword);

            URL url = new URL(urlAddr);
            HttpURLConnection con = openHttpURLConnection(url);

            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
            con.setDoOutput(true);

            writePostData(con, postData);

            if (con.getResponseCode() != 200) {
                return null;
            }

            String result = readHttpResponse(con);
            JsonNode rootNode = parseJson(result);

            JsonNode resultsNode = rootNode.get("results");
            JsonNode commonNode = resultsNode.get("common");
            JsonNode jusoListNode = resultsNode.get("juso");

            int totalCount = Integer.parseInt(commonNode.get("totalCount").asText());
            System.out.println(totalCount);
            ArrayList<BusinessAddrResponse> addrList = new ArrayList<>();

            // 안에 내용 꺼내서 ArrayList에 담기
            int id = 1;
            for (JsonNode jusoNode : jusoListNode) {

                String zipNo = jusoNode.get("zipNo").asText();
                String siNm = jusoNode.get("siNm").asText();
                String roadAddr = jusoNode.get("roadAddr").asText();
                int current = dto.getCurrentPage();

                BusinessAddrResponse businessAddrResponse = new BusinessAddrResponse(id++,zipNo,siNm,roadAddr,current,totalCount);
                addrList.add(businessAddrResponse);
            }

            return addrList.toArray(new BusinessAddrResponse[0]); // ArrayList를 BusinessAddrResponse 배열로 바꿔서 반환

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // 현재 입력한 사업체 정보 유무 체크하는 함수
    @Transactional
    public Business checkValidation(BusinessValidationRequest dto) {
        return businessRepository.checkEntity(dto.getBNo(),dto.getZipCode());
        // 있으면 Business를 반환하고 없으면 null을 반환하게 됨
    }






























    // HTTP 프로토콜을 사용하여 서버와 연결할 객체를 반환
    private HttpURLConnection openHttpURLConnection(URL url) throws IOException {
        return (HttpURLConnection) url.openConnection();
    }

    // post body에 들어갈 data 작성하는 함수
    private void writePostData(HttpURLConnection con, String data) throws IOException {
        try (OutputStream os = con.getOutputStream()) {
            os.write(data.getBytes(StandardCharsets.UTF_8));
            os.flush();
        }
    }

    // post 결과를 string으로 반환하는 함수
    private String readHttpResponse(HttpURLConnection con) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder responseStringBuilder = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                responseStringBuilder.append(line);
            }
            return responseStringBuilder.toString();
        }
    }

    // JSON 문자열읠 파싱해 JsonNode 객체로 변환하는 함수
    private JsonNode parseJson(String jsonString) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readTree(jsonString);
    }


}





