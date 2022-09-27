package com.travelmaker.service;

import com.travelmaker.dto.User;
import com.travelmaker.entity.UserEntity;
import com.travelmaker.error.CustomException;
import com.travelmaker.error.ErrorCode;
import com.travelmaker.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Slf4j
@Service
public class UserServiceImpl implements UserServcie{

    @Autowired
    private UserRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    /* 회원가입 */
    @Override
    public boolean addUser(User user){
        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(user.getPassword());

        UserEntity entity = UserEntity.builder()
                .email(user.getEmail())
                .user_id(user.getId())
                .password(encodedPassword)
                .phone_number(user.getPhone_number())
                .build();

        // DB 저장
        UserEntity savedUser = repository.save(entity);

        if(savedUser.getUser_id().isEmpty())    return false;
        return true;
    }

    /* 중복 아이디 확인 */
    @Override
    public boolean checkId(String id){
        // TODO:FILL THE METHOD!!!
        return true;
    }

    /* 로그인 */
    @Override
    public String login(User user){
        String id = user.getId();
        String password = user.getPassword();
        // user object를 DB에서 가져오기
        Optional<UserEntity> entity = Optional.ofNullable(repository.findByUserId(id)
                // ERROR 던지기
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND)));

        UserEntity findUser = entity.get();
        // DB에 저장된 비밀번호
        String userPW = findUser.getPassword();
        // 비밀번호가 같지 않을 경우
        if(!passwordEncoder.matches(password, userPW)){
            // ERROR 던지기
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }

        return findUser.getUser_id();
    }

    /* 유저 정보 조회 */
    @Override
    public User searchUser(String userId){
        Optional<UserEntity> entity = Optional.ofNullable(repository.findByUserId(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND)));

        UserEntity user = entity.get();

        User findUser = User.builder()
                .id(user.getUser_id())
                .email(user.getEmail())
                .phone_number(user.getPhone_number())
                .profile_img(user.getProfile_img())
                .build();
        return findUser;
    }

    /* 유저 정보 수정 */
    @Override
    public boolean modifyUser(User user){
        Optional<UserEntity> entity = Optional.ofNullable(repository.findByUserId(user.getId())
                // userId가 없는 경우
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND)));

        // params
        String userId = user.getId();
        String email = user.getEmail();
        String password = passwordEncoder.encode(user.getPassword());
        String phone_number = user.getPhone_number();
        String profile_img = user.getProfile_img();
        String role = user.getRole();

        Optional<Integer> updatedUser = Optional.ofNullable(repository.updateUser(userId, email, password, phone_number, profile_img, role)
                // 수정되지 않은 경우
                .orElseThrow(() -> new CustomException(ErrorCode.INTERNAL_SERVER_ERROR)));

        return true;
    }

    /* 회원 탈퇴 */
    public boolean deleteUser(String userId){
        Optional<UserEntity> entity = Optional.ofNullable(repository.findByUserId(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND)));

        UserEntity findUser = entity.get();

        Optional<Integer> deletedUser = Optional.ofNullable(repository.deleteByUserId(findUser.getUser_id())
                // 삭제되지 않은 경우
                .orElseThrow(() -> new CustomException(ErrorCode.INTERNAL_SERVER_ERROR)));
        return true;
    }
}
