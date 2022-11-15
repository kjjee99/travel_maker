package com.travelmaker.controller;

import com.travelmaker.entity.FollowEntity;
import com.travelmaker.entity.UserEntity;
import com.travelmaker.error.CustomException;
import com.travelmaker.error.ErrorCode;
import com.travelmaker.repository.FollowRepository;
import com.travelmaker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/follow")
public class FollowControllerImpl implements FollowController{

    @Autowired
    UserRepository userRepository;
    @Autowired
    FollowRepository followRepository;

    /* 팔로잉 */
    @Override
    @GetMapping("")
    public ResponseEntity following(@CookieValue("userId") String userId, String followId){
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

        return ResponseEntity.ok(HttpStatus.OK);
    }

    /* 팔로잉한 사람 목록 */
    @Override
    @GetMapping("/following/{userId}")
    public ResponseEntity followingList(@PathVariable("userId") String userId){
        List<UserEntity> followings = followRepository.followingList(userId);
        return ResponseEntity.ok(followings);
    }

    /* 팔로우한 사람 목록 */
    @Override
    @GetMapping("/following/{userId}")
    public ResponseEntity followerList(@PathVariable("userId") String userId){
        List<UserEntity> followings = followRepository.followerList(userId);
        return ResponseEntity.ok(followings);
    }

}
