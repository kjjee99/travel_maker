package com.travelmaker.controller;

import com.travelmaker.error.CustomException;
import com.travelmaker.error.ErrorCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@RestController
public class ConfigControllerImpl implements ConfigController {

    /* 쿠키에서 userId 찾기 */
    @Override
    @GetMapping("/id")
    public String extractId(HttpServletRequest request){
        String userId = null;

        Cookie[] cookies = request.getCookies();
        // ERROR: cookie에 값이 없을 경우
        if(cookies.length == 0) throw new CustomException(ErrorCode.NULL_VALUE);
        for(Cookie cookie : cookies){
            if(cookie.getName().equals("userId")){
                userId = cookie.getValue();
            }
        }

        return userId;
    }

    /* 비밀번호 일치 여부 */
    public boolean comparePassword(HttpServletRequest request){
        String userId = extractId(request);

        // Login required Error
        if(userId == null)  throw new CustomException(ErrorCode.LOGIN_REQUIRED);

        // TODO : service 단에서 구현
//        return service.comparePassword(userId);
        return true;
    }
}
