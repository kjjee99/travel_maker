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
                .id(user.getId())
                .password(user.getPassword())
                .post_id("{1,2,3}")
                .role(user.getRole())
                .build();
        UserEntity savedUser = repository.save(entity);

        if(savedUser.getId().isEmpty())    return false;
        return true;
    }

    /* 로그인 */
    @Override
    public String login(User user){
        String id = user.getId();
        String password = user.getPassword();
        UserEntity findUser = repository.findByUserId(id);

        // 회원이 등록되지 않았을 경우
        if(findUser.getId().isEmpty()){
            return null;
        }
        // 비밀번호가 같지 않을 경우
        if(!password.equals(findUser.getPassword())){
            return null;
        }

        return findUser.getId();
    }
}
