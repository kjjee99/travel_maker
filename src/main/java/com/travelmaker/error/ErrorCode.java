package com.travelmaker.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    ID_EXISTS(HttpStatus.FORBIDDEN, "이미 사용중인 아이디 입니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당하는 정보의 사용자를 찾을 수 없습니다."),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다."),
    LOGIN_REQUIRED(HttpStatus.UNAUTHORIZED, "로그인이 필요한 페이지입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "DB에 접근 중 오류가 발생했습니다."),
    NULL_VALUE(HttpStatus.INTERNAL_SERVER_ERROR, "값이 존재하지 않습니다."),
    IS_HEARTED(HttpStatus.FORBIDDEN, "이미 좋아요 누른 글입니다."),
    IS_FOLLOWING(HttpStatus.FORBIDDEN, "이미 팔로우 중인 사용자입니다."),
    ;

    private final HttpStatus status;
    private final String message;
}
