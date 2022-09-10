package com.travelmaker.service;

import com.travelmaker.dto.User;
import com.travelmaker.entity.UserEntity;
import com.travelmaker.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl implements UserServcie{

    @Autowired
    UserRepository repository;

    /* 회원가입 */
    @Override
    public boolean addUser(User user){
        UserEntity entity = UserEntity.builder()
                .email(user.getEmail())
                .user_id(user.getId())
                .password(user.getPassword())
                .post_id("{1,2,3}")
                .role(user.getRole())
                .build();
        UserEntity savedUser = repository.save(entity);

        if(savedUser.getUser_id().isEmpty())    return false;
        return true;
    }

    /* 로그인 */
    @Override
    public String login(User user){
        String id = user.getId();
        String password = user.getPassword();
        UserEntity findUser = repository.findByUserId(id);

        // 회원이 등록되지 않았을 경우
        if(findUser.getUser_id().isEmpty()){
            return null;
        }
        // 비밀번호가 같지 않을 경우
        if(!password.equals(findUser.getPassword())){
            return null;
        }

        return findUser.getUser_id();
    }

    /* 유저 정보 수정 */
    @Override
    public boolean modifyUser(User user){
        UserEntity findUser = repository.findByUserId(user.getId());

        // 찾는 유저가 없을 경우
        if(findUser.getUser_id().isEmpty()){
            return false;
        }

        // params
        String userId = user.getId();
        String email = user.getEmail();
        String password = user.getPassword();
        String profile_img = user.getProfile_img();
        String role = user.getRole();

        int updatedUser = repository.updateUser(userId, email, password, profile_img, role);

        // 수정되지 않은 경우
        if(updatedUser == -1){
            return false;
        }

        return true;
    }

    /* 회원 탈퇴 */
    public boolean deleteUser(User user){
        UserEntity findUser = repository.findByUserId(user.getId());

        // 유저 정보가 존재하지 않을 경우
        if(findUser.getUser_id().isEmpty()) return false;

        int deletedUser = repository.deleteByUserId(findUser.getUser_id());

        // 삭제되지 않은 경우
        if(deletedUser == -1)   return false;
        return true;
    }
}
