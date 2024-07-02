package com.eb.earbee.business.repository;

import com.eb.earbee.business.entity.Business;
import com.eb.earbee.business.request.BusinessValidationRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface BusinessRepository extends CrudRepository<Business, Long> {

    @Override
    ArrayList<Business> findAll();

    @Query("select e from Business e where e.bNo = :bNo And e.zipCode = :zipCode ")
    Business checkEntity(int bNo, int zipCode);
}
