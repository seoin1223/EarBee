package com.eb.earbee.main.repository;


import com.eb.earbee.main.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findById(String id);

}
