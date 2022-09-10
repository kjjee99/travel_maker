package com.travelmaker.controller;

import com.travelmaker.entity.UserEntity;
import com.travelmaker.dto.User;
import com.travelmaker.repository.UserRepository;
import com.travelmaker.service.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api")
public class UserControllerImpl implements UserController {

    @Autowired
    UserServiceImpl service;
    @Autowired
    UserRepository repository;

    @GetMapping("/home")
    public void home(){
        log.info("home");
        System.out.println("home");
    }

    /* 회원가입 */
    @Override
    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity addUser(@RequestBody User user){
        boolean result = service.addUser(user);
        
        if(!result){
            return ResponseEntity.ok(HttpStatus.FORBIDDEN);
        }
        return ResponseEntity.ok(HttpStatus.OK);
    }

    /* 로그인 */
    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity login(@RequestBody User user){
        boolean result = service.addUser(user);

        if(!result){
            return ResponseEntity.ok(HttpStatus.FORBIDDEN);
        }
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
