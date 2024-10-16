package com.eb.earbee.main.repository;


import com.eb.earbee.main.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u ORDER BY u.created ASC")
    List<User> findAllUsers();

    User findById(String id);
    User findByAlias(String username);

}
