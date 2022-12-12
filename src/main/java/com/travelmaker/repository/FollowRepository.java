package com.travelmaker.repository;

import com.travelmaker.entity.FollowEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Repository
public interface FollowRepository extends JpaRepository<FollowEntity, String> {
    @Transactional
    @Modifying
    @Query(value = "delete from follow where useridx = :userIdx and following = :followingIdx", nativeQuery = true)
    Optional<Integer> unfollow(int userIdx, int followingIdx);
}
