package com.travelmaker.service;

import com.travelmaker.dto.User;
import com.travelmaker.entity.UserEntity;
import com.travelmaker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
