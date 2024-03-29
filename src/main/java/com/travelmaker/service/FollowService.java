package com.travelmaker.service;

import com.travelmaker.entity.UserEntity;

import java.util.List;

public interface FollowService {

    /* 팔로우 */
    boolean follow(String userId, String followId);

    /* 팔로우 취소 */
    boolean unfollow(String userId, String followId);

    /* 팔로우 확인 */
    boolean checkFollow(String userId, String followId);

    /* 팔로잉한 사람 목록 */
    List<UserEntity> followingList(String userId, int pageNumber);

    /* 팔로우한 사람 목록 */
    List<UserEntity> followerList(String userId, int pageNumber);
}
