package com.eb.earbee.security.repository;

import com.eb.earbee.main.entity.User;
import com.eb.earbee.security.entity.TemporaryUser;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public interface TemporaryUserRespository extends JpaRepository<TemporaryUser, Long> {
    TemporaryUser findById(String id);

}
