package com.travelmaker.service;

import com.travelmaker.entity.FollowEntity;
import com.travelmaker.entity.UserEntity;
import com.travelmaker.error.CustomException;
import com.travelmaker.error.ErrorCode;
import com.travelmaker.repository.FollowRepository;
import com.travelmaker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FollowServiceImpl implements FollowService{

    @Autowired
    UserRepository userRepository;
    @Autowired
    FollowRepository followRepository;

    public boolean follow(String userId, String followId){
        // 팔로우하는 유저
        Optional<Integer> follower = userRepository.findIdByUserId(userId);
        int followerId = follower.get();

        // 팔로잉하는 유저
        Optional<Integer> following = userRepository.findIdByUserId(followId);
        int followingId = following.get();

        // 저장하기
        FollowEntity entity = FollowEntity.builder()
                .userId(followerId)
                .following(followingId)
                .build();
        FollowEntity result = followRepository.save(entity);
        // ERROR
        if(result.getUserId() == 0) new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);

        return true;
    }

    /* 팔로잉한 사람 목록 */
    public List<UserEntity> followingList(String userId){
        List<UserEntity> followings = followRepository.followingList(userId);
        return followings;
    }

    /* 팔로우한 사람 목록 */
    public List<UserEntity> followerList(String userId){
        List<UserEntity> followers = followRepository.followerList(userId);
        return followers;
    }
}
