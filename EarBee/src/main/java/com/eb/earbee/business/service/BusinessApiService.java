package com.eb.earbee.business.service;


import com.eb.earbee.business.request.BusinessAddrRequest;
import com.eb.earbee.business.request.BusinessApplyRequest;

import com.eb.earbee.business.response.BusinessAddrResponse;
import com.eb.earbee.business.response.BusinessApplyResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.net.HttpURLConnection;


import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
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

    @Value("${address.key}")
    String confmKey;
    @Value("${address.url}")
    private String urlAddr;

    // 변수값이 정상적으로 들어오는지 확인하는 메서드
    public List<String> checkValue() {
        return Arrays.asList(urlBusiness, encodingKey, decodingKey);
    }


    // 사업자 번호 조회 메서드
    @Transactional
    public BusinessApplyResponse businessSearchNum(BusinessApplyRequest dto) {
        StringBuilder str = new StringBuilder(); // url 넣을 stringBuilder
        str.append(urlBusiness);
        str.append(encodingKey);
        BufferedReader br;
        String result = "";

        try {
            URL url = new URL(str.toString()); // url 생성
            HttpURLConnection con = (HttpURLConnection) url.openConnection(); // api 연결할 객체 생성 이떄 url은 생성한 url
            con.setRequestMethod("POST"); // 전송방식 post
            con.setRequestProperty("Content-Type", "application/json; charset = utf-8"); // request body 전송을 aspplcation/json으로 서버 전달
            con.setDoOutput(true); // outputStream으로 post data를 넘김

            OutputStream os = con.getOutputStream(); // Request body에 넣을 data가 담긴 OutputStream
            os.write(dto.getJsonApply().getBytes(StandardCharsets.UTF_8)); // outStream에 body 삽입 utf-8방식으로
            os.flush();
            os.close();


            if (con.getResponseCode() != 200) { // 응답코드가 200이 아니면 null 반환
                return null;
            } else { // 제공받은 내용을 bufferreade에 저장
                br = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8));
            }
            result = br.readLine();
            br.close();

            // String -> JSON 변경 후 자바 객체로 변경
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(result); // 전체 JSON parser
            Object match_cnt = jsonObject.get("match_cnt"); // 매칭된 데이터 수를 Object에 임시 저장

            if (match_cnt != null) {
                String cnt = jsonObject.get("match_cnt").toString(); // null이 아니면 cnt에 매칭된 수 저장

                // JSON data의 배열을 저장
                JSONArray jsonArray = (JSONArray) jsonObject.get("data");
                JSONObject data = (JSONObject) jsonArray.get(0);

                // BusinessResponse에 담아 return
                String b_no = String.valueOf(data.get("b_no"));
                String b_stt_cd = String.valueOf(data.get("b_stt_cd"));

                return new BusinessApplyResponse(Integer.parseInt(b_no), Integer.parseInt(b_stt_cd));
            }
            System.out.println("조회 결과 없음");
            return null;
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public BusinessAddrResponse searchAddr(BusinessAddrRequest dto) {
        try {
            // 검색어 인코딩
            String encodedKeyword = URLEncoder.encode(dto.getKeyword(), StandardCharsets.UTF_8.toString());

            // 검색어가 입력되지 않았을 때의 처리
            if (encodedKeyword == null || encodedKeyword.trim().isEmpty()) {
                System.out.println("검색어가 입력되지 않았습니다.");
                return null;
            }

            // 데이터를 x-www-form-urlencoded 형식으로 만들기
            String postData = "confmKey=" + URLEncoder.encode(confmKey, StandardCharsets.UTF_8.toString())
                    + "&currentPage=1"
                    + "&keyword=" + encodedKeyword
                    + "&countPerPage=10"
                    + "&resultType=json";

            // HTTP 요청 보내기
            URL url = new URL(urlAddr);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
            con.setDoOutput(true);

            try (OutputStream os = con.getOutputStream()) {
                os.write(postData.getBytes(StandardCharsets.UTF_8));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            // HTTP 응답 처리
            if (con.getResponseCode() != 200) {
                return null;
            }

            // JSON 문자열을 읽기
            try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))) {
                StringBuilder responseStringBuilder = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    responseStringBuilder.append(line);
                }
                // 결과 String
                String result = responseStringBuilder.toString();
                JSONParser jsonParser = new JSONParser();
                JSONObject jsonObject = (JSONObject) jsonParser.parse(result);
                ObjectMapper objectMapper = new ObjectMapper();
                String prettyJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);
                System.out.println(prettyJson);

                JSONObject results = (JSONObject) jsonObject.get("results");
                JSONArray jusoList = (JSONArray) results.get("juso");

                for (Object juso : jusoList) {
                    JSONObject jusoDetails = (JSONObject) juso;
                    String zipNo = (String) jusoDetails.get("zipNo");
                    System.out.println("zipNo: " + zipNo);
                }

            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

                return null; // 적절한 리턴값으로 변경
            } catch (ParseException | IOException e) {
                throw new RuntimeException(e);
            }

        }
    }




