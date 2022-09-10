package com.travelmaker.controller;

import com.travelmaker.dto.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletResponse;

public interface UserController {

    /* 회원가입 */
    ResponseEntity addUser(@RequestBody User user);

    /* 로그인 */
    ResponseEntity login(@RequestBody User user, HttpServletResponse response);

    /* 로그아웃 */
    ResponseEntity logout(HttpServletResponse response);

    /* 유저 정보 수정 */
    ResponseEntity modifyUser(@RequestBody User user);

    /* 회원 탈퇴 */
    ResponseEntity deleteUser(@RequestBody User user, HttpServletResponse response);
}
