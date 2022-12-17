package com.travelmaker.controller;

import com.travelmaker.dto.Post;
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
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api")
public class UserControllerImpl{

    @Autowired
    UserServiceImpl service;

    /* 회원가입 */
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
    @GetMapping("/check")
    public ResponseEntity checkId(@RequestParam String id){
        service.checkId(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    /* 로그인 */
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
    @GetMapping("/info/{userId}")
    public ResponseEntity<?> userInfo(@PathVariable("userId") String userId){
        // 프론트에서 받는 타입(json)
        return ResponseEntity.ok(service.searchUser(userId));
    }

    /* 유저 정보 수정 */
    @PostMapping("/user")
    public ResponseEntity<?> modifyUser(@CookieValue("userId") String userId,
                                        @RequestPart("user") User user,
                                        @RequestPart("profileImg") MultipartFile image) {
        user.setId(userId);
        service.modifyUser(user, image);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    /* 비밀번호 변경 */
    @PostMapping("/user/pass")
    public ResponseEntity modifyPass(@CookieValue("userId") String userId, @RequestBody Map<String, String> param){
        String nowPassword = param.get("nowPassword");
        String newPassword = param.get("newPassword");

        User user = User.builder().id(userId).password(nowPassword).build();
        service.modifyPass(user, newPassword);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    /* 회원 탈퇴 */
    @PostMapping("/sign-out")
    public ResponseEntity deleteUser(@CookieValue("userId") String userId, HttpServletResponse response, @RequestBody Map<String, String> param){
        String password = param.get("password");
        boolean result = service.deleteUser(userId, password);

        // 쿠키에서 삭제
        ResponseCookie cookie = ResponseCookie.from("userId", null)
                .maxAge(0)
                .path("/").build();

        response.addHeader("Set-Cookie", cookie.toString());

        return ResponseEntity.ok(HttpStatus.OK);
    }

    /* 회원 검색 */
    @GetMapping("/user")
    public ResponseEntity searchUser(@RequestParam String word){
        return ResponseEntity.ok(service.searchUserByKeyword(word));
    }
    // 비밀번호 찾기 => 추가 기능
}
