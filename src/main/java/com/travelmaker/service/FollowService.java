package com.travelmaker.service;

import com.travelmaker.entity.UserEntity;

import java.util.List;

public interface FollowService {

    /* 팔로우 */
    boolean follow(String userId, String followId);

    /* 팔로잉한 사람 목록 */
    List<UserEntity> followingList(String userId);

    /* 팔로우한 사람 목록 */
    List<UserEntity> followerList(String userId);
}
