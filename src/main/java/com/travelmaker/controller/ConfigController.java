package com.travelmaker.controller;


import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

public interface ConfigController {

    /* 쿠키에서 유저 아이디 추출 */
    String extractId(HttpServletRequest request);

    /* 비밀번호 일치 여부 */
    boolean comparePassword(HttpServletRequest request);
}
