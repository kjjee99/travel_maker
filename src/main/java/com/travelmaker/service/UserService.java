package com.travelmaker.service;

import com.travelmaker.dto.User;
import com.travelmaker.entity.UserEntity;

import java.util.List;

public interface UserService {

    /* 회원가입 */
    boolean addUser(User user);

    /* 중복 아이디 확인 */
    boolean checkId(String id);

    /* 로그인 */
    String login(User user);

    /* 유저 페이지 조회 */
    User searchUser(String userId);

    /* 유저 정보 수정 */
    boolean modifyUser(User user);

    /* 비밀번호 확인 */
    boolean checkPassword(String password, String compare);

    /* 비밀번호 수정 */
    boolean modifyPass(User user, String newPassword);

    /* 회원 탈퇴 */
    boolean deleteUser(String userId, String password);

    /* 회원 검색 */
    List<UserEntity> searchUserByKeyword(String word);
}
