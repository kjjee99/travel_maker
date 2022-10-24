package com.travelmaker.controller;

import com.travelmaker.dto.User;
import com.travelmaker.entity.UserEntity;
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
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
public class UserControllerImpl implements UserController {

    @Autowired
    UserServiceImpl service;
    @Autowired
    ConfigControllerImpl middleware;

    // TODO: @ResponseBody가 없어도 실행되는지 확인

    /* 회원가입 */
    @Override
    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity addUser(@RequestBody User user){
        boolean result = service.addUser(user);

        // 성공했을 때
        return ResponseEntity.ok(HttpStatus.OK);
    }

    /* 중복 아이디 확인 */
    @Override
    @GetMapping("/check")
    @ResponseBody
    public ResponseEntity checkId(@RequestParam String id){
        service.checkId(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    /* FIXME: 비밀번호 확인 */
    @Override
    @PostMapping("/checkpw")
    @ResponseBody
    public ResponseEntity checkPassword(@RequestBody String password, HttpServletRequest request, HttpServletResponse response){

        // 쿠키에서 userId 찾기
        String userId = middleware.extractId(request);

        // TODO: login()
        User user = User.builder().id(userId)
                        .password(password)
                                .build();
        service.login(user);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    /* 로그인 */
    @Override
    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity login(@RequestBody User user, HttpServletResponse response){
        String id = service.login(user);

        // 쿠키 저장
        Cookie cookie = new Cookie("userId", id);
        cookie.setMaxAge(60 * 60 * 3);   // 3 hours
        cookie.setPath("/");            // 모든 경로에서 접근 가능
        cookie.setHttpOnly(true);       // 브라우저에서 쿠키 접근 X
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

    /* 유저 정보 조회 */
    @Override
    @GetMapping("/info")
    @ResponseBody
    public User searchUser(HttpServletRequest request){
        String userId = null;

        // 쿠키에서 userId 찾기
        userId = middleware.extractId(request);

        // 프론트에서 받는 타입(json)
        return service.searchUser(userId);
    }

    /* 유저 정보 수정 */
    @Override
    @PostMapping("/user")
    @ResponseBody
    public ResponseEntity modifyUser(HttpServletRequest request,@RequestBody User user) {
        String userId = middleware.extractId(request);

        boolean result = service.modifyUser(user);
        if(!result) return ResponseEntity.ok(HttpStatus.FORBIDDEN);

        // TODO: 수정된 정보 반환
        return ResponseEntity.ok(HttpStatus.OK);
    }

    /* 회원 탈퇴 */
    @Override
    @GetMapping("/sign-out")
    @ResponseBody
    public ResponseEntity deleteUser(HttpServletRequest request, HttpServletResponse response){
        // 쿠키에서 userId 찾기
        String userId = middleware.extractId(request);

        boolean result = service.deleteUser(userId);
        if(!result) return ResponseEntity.ok(HttpStatus.FORBIDDEN);

        // 존재하던 쿠키 삭제
        Cookie cookie = new Cookie("userId", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");

        response.addCookie(cookie);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    /* 회원 검색 */
    @Override
    @GetMapping("/user")
    public List<UserEntity> searchUser(@RequestParam String word){
        return service.searchUserByKeyword(word);
    }
    // 비밀번호 찾기 => 추가 기능
}
