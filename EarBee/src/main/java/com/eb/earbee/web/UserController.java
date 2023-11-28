package com.eb.earbee.web;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("test")
public class UserController {

    @GetMapping("/user")
    public void findAll(){
        // 모든 user object 반환
        System.out.println("UserController.findAll");

    }

    @GetMapping("/user/{id}")
    public void findById(@PathVariable int id){
        // 특정 user object 반환
        System.out.println("UserController.findById");
    }

    @PostMapping("/user")
    public void Save(String username, String password, String phone){
        // user 생성
        System.out.println("UserController.Save");
    }

    @DeleteMapping("/user/{id}")
    public void delete(@PathVariable int id){
        // user 삭제
        System.out.println("UserController.delete");
    }

    @PutMapping("/user/{id}")
    public void update(String password, String phone, @PathVariable int id){
        // user 수정
        System.out.println("UserController.update");
    }
}
