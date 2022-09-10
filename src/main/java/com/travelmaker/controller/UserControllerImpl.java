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

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
@RequestMapping("/api")
public class UserControllerImpl implements UserController {

    @Autowired
    UserServiceImpl service;
    @Autowired
    UserRepository repository;

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
    @Override
    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity login(@RequestBody User user, HttpServletResponse response){
        String id = service.login(user);

        // TODO: error 발생
        if(id == null){
            return ResponseEntity.ok(HttpStatus.FORBIDDEN);
        }

        // TODO: 쿠키 암호화
        // 쿠키 저장
        Cookie cookie = new Cookie("userId", id);
        cookie.setMaxAge(1000 * 60 * 60 * 3);   // 3 hours
        cookie.setPath("/");        // 모든 경로에서 접근 가능
        cookie.setHttpOnly(true);   // 브라우저에서 쿠키 접근 X
        response.addCookie(cookie);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    /* 로그아웃 */
    @Override
    @GetMapping("/logout")
    @ResponseBody
    public ResponseEntity logout(HttpServletResponse response){
        // 쿠키에서 삭제
        Cookie cookie = new Cookie("userId", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");

        response.addCookie(cookie);

        return ResponseEntity.ok(HttpStatus.OK);
    }

}
