package com.travelmaker.controller;

import com.travelmaker.dto.User;
import com.travelmaker.entity.UserEntity;
import com.travelmaker.service.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
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

    /* 회원가입 */
    @Override
    @PostMapping("/register")
    public ResponseEntity addUser(@RequestBody User user, HttpServletResponse response){
        boolean result = service.addUser(user);

        // 자동 로그인
        if(result){
            // 쿠키 저장
            ResponseCookie cookie = ResponseCookie.from("userId", user.getId())
                    .maxAge(60 * 60 * 3)   // 3 hours
                    .path("/")            // 모든 경로에서 접근 가능
                    .httpOnly(true)       // 브라우저에서 쿠키 접근 X
                    .build();

            response.setHeader("Set-Cookie", cookie.toString());
        }

        // 성공했을 때
        return ResponseEntity.ok(HttpStatus.OK);
    }

    /* 중복 아이디 확인 */
    @Override
    @GetMapping("/check")
    public ResponseEntity checkId(@RequestParam String id){
        service.checkId(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    /* 로그인 */
    @Override
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody User user, HttpServletResponse response){
        String id = service.login(user);

        // 쿠키 저장
        ResponseCookie cookie = ResponseCookie.from("userId", id)
        .maxAge(60 * 60 * 3)   // 3 hours
        .path("/")            // 모든 경로에서 접근 가능
        .httpOnly(true)       // 브라우저에서 쿠키 접근 X
        .build();

        response.setHeader("Set-Cookie", cookie.toString());
        return ResponseEntity.ok(HttpStatus.OK);
    }

    /* 로그아웃 */
    @Override
    @GetMapping("/logout")
    public ResponseEntity logout(HttpServletResponse response){
        // 쿠키에서 삭제
        ResponseCookie cookie = ResponseCookie.from("userId", null)
                        .maxAge(0)
                                .path("/").build();

        response.addHeader("Set-Cookie", cookie.toString());

        return ResponseEntity.ok(HttpStatus.OK);
    }

    /* 유저 정보 조회 */
    @Override
    @GetMapping("/info/{userId}")
    public ResponseEntity<?> searchUser(@PathVariable("userId") String userId, HttpServletRequest request){
        // 프론트에서 받는 타입(json)
        return ResponseEntity.ok(service.searchUser(userId));
    }

    /* 유저 정보 수정 */
    @Override
    @PostMapping("/user")
    public ResponseEntity<?> modifyUser(@CookieValue("userId") String userId, HttpServletRequest request,@RequestBody User user) {
        service.modifyUser(user);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    // FIXME: @RequestBody
    /* 회원 탈퇴 */
    @Override
    @GetMapping("/sign-out")
    public ResponseEntity deleteUser(@CookieValue("userId") String userId, HttpServletRequest request, HttpServletResponse response, @RequestBody User user){
        boolean result = service.deleteUser(userId, user.getPassword());

        // 쿠키에서 삭제
        ResponseCookie cookie = ResponseCookie.from("userId", null)
                .maxAge(0)
                .path("/").build();

        response.addHeader("Set-Cookie", cookie.toString());

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
