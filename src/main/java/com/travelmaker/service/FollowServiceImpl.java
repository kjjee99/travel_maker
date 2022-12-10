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

    /* 팔로우 */
    @Override
    public boolean follow(String userId, String followId){
        // 팔로우하는 유저
        Optional<Integer> follower = Optional.ofNullable(userRepository.findIdByUserId(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND)));
        int followerIdx = follower.get();

        // 팔로잉하는 유저
        Optional<Integer> following = Optional.ofNullable(userRepository.findIdByUserId(followId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND)));
        int followingIdx = following.get();

        // 저장하기
        FollowEntity entity = FollowEntity.builder()
                .userIdx(followerIdx)
                .following(followingIdx)
                .build();
        FollowEntity result = followRepository.save(entity);
        // ERROR
        if(result.getUserIdx() == 0) new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);

        return true;
    }

    /* 팔로잉한 사람 목록 */
    @Override
    public List<UserEntity> followingList(String userId){
        Optional<Integer> id = Optional.ofNullable(userRepository.findIdByUserId(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND)));
        int findId = id.get();

        List<UserEntity> followings = userRepository.followingList(findId);

        if(followings.size() < 1)   throw new CustomException(ErrorCode.NULL_VALUE);
        return followings;
    }

    /* 팔로우한 사람 목록 */
    @Override
    public List<UserEntity> followerList(String userId){
        Optional<Integer> id = Optional.ofNullable(userRepository.findIdByUserId(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND)));
        int findId = id.get();

        List<UserEntity> followers = userRepository.followerList(findId);
        if(followers.size() < 1)   throw new CustomException(ErrorCode.NULL_VALUE);
        return followers;
    }
}
