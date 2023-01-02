package com.travelmaker.repository;

import com.travelmaker.entity.FollowEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Repository
public interface FollowRepository extends JpaRepository<FollowEntity, String> {
    // 언팔로우
    @Transactional
    @Modifying
    @Query(value = "delete from follow where useridx = :userIdx and following = :followingIdx", nativeQuery = true)
    Optional<Integer> unfollow(int userIdx, int followingIdx);

    // 팔로우 중인지 확인
    @Query(value = "select count(*) from follow where useridx = :userIdx and following = :followingIdx", nativeQuery = true)
    Optional<Integer> checkFollowing(@Param("userIdx") int userIdx, @Param("followingIdx") int followingIdx);

    // 팔로잉 숫자
    @Query(value = "select count(*) from follow where useridx = :userIdx", nativeQuery = true)
    Optional<Integer> countFollowingByUser(@Param("userIdx") int userIdx);

    // 팔로워 숫자
    @Query(value = "select count(*) from follow where following = :userIdx", nativeQuery = true)
    Optional<Integer> countFollowerByUser(@Param("userIdx") int userIdx);

    // 회원 탈퇴 시 팔로우 삭제
    @Transactional
    @Modifying
    @Query(value = "delete from follow where useridx = :userIdx or following =:userIdx", nativeQuery = true)
    Optional<Integer> removeFollows(@Param("userIdx") int userIdx);
}
