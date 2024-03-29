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
@RequestMapping("/api/follow")
public class FollowControllerImpl{

    @Autowired
    FollowServiceImpl followService;

    /* 팔로잉 */
    @GetMapping("/{followId}")
    public ResponseEntity following(@CookieValue("userId") String userId, @PathVariable("followId") String followId){
        followService.follow(userId, followId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    /* 팔로우 취소 */
    @GetMapping("/cancel/{followId}")
    public ResponseEntity unfollow(@CookieValue("userId") String userId, @PathVariable("followId") String followId){
        followService.unfollow(userId, followId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    /* 팔로우 확인 */
    @GetMapping("/check/{followId}")
    public ResponseEntity checkFollow(@CookieValue("userId") String userId, @PathVariable("followId") String followId){
        followService.checkFollow(userId, followId);
        return ResponseEntity.ok(HttpStatus.OK);
    }


    /* 팔로잉한 사람 목록 */
    @GetMapping("/following/{userId}")
    public ResponseEntity followingList(@PathVariable("userId") String userId, @RequestParam("page") int pageNumber){
        List<UserEntity> followings = followService.followingList(userId, pageNumber);
        return ResponseEntity.ok(followings);
    }

    /* 팔로우한 사람 목록 */
    @GetMapping("/follower/{userId}")
    public ResponseEntity followerList(@PathVariable("userId") String userId, @RequestParam("page") int pageNumber){
        List<UserEntity> followers = followService.followerList(userId, pageNumber);
        return ResponseEntity.ok(followers);
    }

}
