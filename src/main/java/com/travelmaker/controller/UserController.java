package com.travelmaker.controller;

import com.travelmaker.dto.User;
import com.travelmaker.entity.UserEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface UserController {

    /* 회원가입 */
    ResponseEntity addUser(@RequestBody User user);

    /* 중복 아이디 확인 */
    ResponseEntity checkId(@RequestParam String id);

    /* 비밀번호 확인 */
    ResponseEntity checkPassword(String password, HttpServletRequest request, HttpServletResponse response);

    /* 로그인 */
    ResponseEntity login(@RequestBody User user, HttpServletResponse response);

    /* 로그아웃 */
    ResponseEntity logout(HttpServletResponse response);

    /* 유저 정보 조회 */
    User searchUser(HttpServletRequest request);

    /* 유저 정보 수정 */
    ResponseEntity modifyUser(HttpServletRequest request, @RequestBody User user);

    /* 회원 탈퇴 */
    ResponseEntity deleteUser(HttpServletRequest request, HttpServletResponse response);

    /* 회원 검색 */
    List<UserEntity> searchUser(@RequestParam String word);
}
