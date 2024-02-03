package com.eb.earbee.business.service;

import com.eb.earbee.business.entity.Business;
import com.eb.earbee.business.repository.BusinessRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BusinessServiceTest {

    @Autowired BusinessService businessService;
    @Autowired
    BusinessRepository businessRepository;
    @Test
    @DisplayName("사업체 등록")
    void addBusiness() {
        {
            // 정상적으로 추가가 되었는지 확인하는 메서드
            // Data
            Business business = new Business(1L,"1308189095","44220","울산광역시 북구 매곡1로 17 (매곡동, 매곡푸르지오2단지)","109/204");
            Business result = businessRepository.save(business);

            // 비교
            assertEquals(result,business,"실패");
        }

        
    }
}