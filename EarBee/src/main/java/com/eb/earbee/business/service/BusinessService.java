package com.eb.earbee.business.service;

import com.eb.earbee.business.dto.BusinessForm;
import com.eb.earbee.business.entity.Business;
import com.eb.earbee.business.repository.BusinessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BusinessService  {

    @Autowired
    BusinessRepository businessRepository;

    // 비즈니스 게시판 모든 게시글 조회
    public List<Business> findAll(Pageable pageable) {
        return businessRepository.findAllInReverseOrder(pageable);
    }

    @Transactional
    public Business addBusiness(BusinessForm entity, String user) {
        Business business = entity.toEntity();
        business.setAuthor(user);

        return businessRepository.save(business);

    }


}
