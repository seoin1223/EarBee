package com.eb.earbee.signin.repository;

import com.eb.earbee.signin.entiry.SignIn;



import java.util.List;
import java.util.Optional;

public interface SignInRepository {
    SignIn save(SignIn signIn);
    Optional<SignIn> findById(Long id); // null이 반환될수 있으니 Optional 사용
    Optional<SignIn> findByName(String name);
    List<SignIn> findAll();

}
