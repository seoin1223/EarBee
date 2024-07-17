package com.eb.earbee.business.repository;

import com.eb.earbee.business.entity.Business;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface BusinessRepository extends JpaRepository<Business, Integer> {

    @Query("SELECT b FROM Business b ORDER BY b.id DESC")
    List<Business> findAllInReverseOrder(Pageable pageable);

    @Query("select e from Business e where e.bNo = :bNo And e.zipCode = :zipCode")
    Business checkEntity(int bNo, int zipCode);
}


