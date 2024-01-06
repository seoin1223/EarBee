package com.eb.earbee.signin.repository;

import com.eb.earbee.signin.entiry.SignIn;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Member;
import java.util.*;

@Repository
public class MemorySignInRepository implements SignInRepository{

    private static Map<Long, SignIn> store = new HashMap<>();
    private static long sequence = 0L; // 키값 0,1,2,...


    @Override
    public SignIn save(SignIn signin) {
        signin.setId(++sequence); // id 세팅
        store.put(signin.getId(), signin); // store에 저장
        return signin;
    }

    @Override
    public Optional<SignIn> findById(Long id) {
        return Optional.ofNullable(store.get(id)); // null이 반환될수있으니 Optional로 감쌈
    }

    @Override
    public Optional<SignIn> findByName(String name) {
        return store.values().stream()
                .filter(signIn -> signIn.getName().equals(name)) // signIn.getName()이 Name이랑 같은지 확인 찾으면 반환
                .findAny();
    }

    @Override
    public List<SignIn> findAll() {
        return new ArrayList<>(store.values()); // 스토어 반환
    }

    public void clearStore(){
        store.clear();
    }
}
