package com.travelmaker.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;

public interface FollowController {

    /* 팔로잉 */
    ResponseEntity following(@CookieValue("userId") String userId, @PathVariable("followId") String followId);

    /* 팔로잉한 사람 목록 */
    ResponseEntity followingList(@PathVariable("userId") String userId);

    /* 팔로우한 사람 목록 */
    ResponseEntity followerList(@PathVariable("userId") String userId);

}
