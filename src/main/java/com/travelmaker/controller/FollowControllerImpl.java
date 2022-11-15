package com.travelmaker.controller;

import com.travelmaker.entity.FollowEntity;
import com.travelmaker.entity.UserEntity;
import com.travelmaker.error.CustomException;
import com.travelmaker.error.ErrorCode;
import com.travelmaker.repository.FollowRepository;
import com.travelmaker.repository.UserRepository;
import com.travelmaker.service.FollowService;
import com.travelmaker.service.FollowServiceImpl;
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
    FollowServiceImpl followService;

    /* 팔로잉 */
    @Override
    @GetMapping("/{followId}")
    public ResponseEntity following(@CookieValue("userId") String userId, @PathVariable("followId") String followId){
        followService.follow(userId, followId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    /* 팔로잉한 사람 목록 */
    @Override
    @GetMapping("/following/{userId}")
    public ResponseEntity followingList(@PathVariable("userId") String userId){
        List<UserEntity> followings = followService.followingList(userId);
        return ResponseEntity.ok(followings);
    }

    /* 팔로우한 사람 목록 */
    @Override
    @GetMapping("/follower/{userId}")
    public ResponseEntity followerList(@PathVariable("userId") String userId){
        List<UserEntity> followers = followService.followerList(userId);
        return ResponseEntity.ok(followers);
    }

}
