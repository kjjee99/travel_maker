package com.travelmaker.service;

import com.travelmaker.dto.User;

public interface UserServcie {

    /* 회원가입 */
    boolean addUser(User user);

    /* 로그인 */
    String login(User user);

    /* 유저 페이지 */

    /* 유저 정보 수정 */
    boolean modifyUser(User user);

    /* 회원 탈퇴 */
    boolean deleteUser(User user);

    /* 비밀번호 찾기 */
}
