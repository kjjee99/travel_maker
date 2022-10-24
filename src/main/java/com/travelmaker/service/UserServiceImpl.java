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
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserServiceImpl implements UserService{

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

        if(savedUser.getUser_id().isEmpty())    throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        return true;
    }

    /* 중복 아이디 확인 */
    @Override
    public boolean checkId(String id){
        Optional<UserEntity> entity = repository.findByUserId(id);
        // 아이디가 존재하는 경우 에러 발생
        if(!entity.get().getUser_id().isEmpty()) throw new CustomException(ErrorCode.ID_EXISTS);
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

        UserEntity findUser = entity.get();

        String password = passwordEncoder.encode(user.getPassword());
        // 비밀번호 맞는지 확인하기
        checkPassword(password, findUser.getPassword());

        // params
        String userId = user.getId().isEmpty() ? findUser.getUser_id() : user.getId();   // 수정 안됨
        String email = user.getEmail().isEmpty() ? findUser.getEmail() : user.getEmail();
        String phone_number = user.getPhone_number().isEmpty() ? findUser.getPhone_number() : user.getPhone_number();
        String profile_img = user.getProfile_img().isEmpty() ? findUser.getProfile_img() : user.getProfile_img();

        Optional<Integer> updatedUser = Optional.ofNullable(repository.updateUser(userId, email, password, phone_number, profile_img)
                // 수정되지 않은 경우
                .orElseThrow(() -> new CustomException(ErrorCode.INTERNAL_SERVER_ERROR)));

        return true;
    }

    /* 비밀번호 확인 */
    public boolean checkPassword(String password, String compare){
        if(!passwordEncoder.matches(password, compare))
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        return true;
    }

    /* 회원 탈퇴 */
    public boolean deleteUser(String userId, String password){
        Optional<UserEntity> entity = Optional.ofNullable(repository.findByUserId(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND)));

        UserEntity findUser = entity.get();

        Optional<Integer> deletedUser = Optional.ofNullable(repository.deleteByUserId(findUser.getUser_id())
                // 삭제되지 않은 경우
                .orElseThrow(() -> new CustomException(ErrorCode.INTERNAL_SERVER_ERROR)));
        return true;
    }

    /* 회원 검색 */
    @Override
    public List<UserEntity> searchUserByKeyword(String word){
        List<UserEntity> list = repository.findByKeyword(word);
        // ERROR: 값이 존재하지 않을 때
        if(list.size() == 0)    throw new CustomException(ErrorCode.NULL_VALUE);

        return list;
    }
}
