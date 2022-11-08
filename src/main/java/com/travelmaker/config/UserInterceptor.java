package com.travelmaker.config;

import com.travelmaker.error.CustomException;
import com.travelmaker.error.ErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Component
public class UserInterceptor implements HandlerInterceptor {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    // 컨트롤러가 실행되기 전 쿠키에 값이 있는지 확인한 후 존재하면 true, 없으면 NULL_VALUE 에러 발생
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();
        if(cookies == null)  throw new CustomException(ErrorCode.NULL_VALUE);

        for(Cookie cookie : cookies){
            if(cookie.getName().equals("userId")) return true;
        }

        return false;
    }
}
