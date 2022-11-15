package com.travelmaker.repository;

import com.travelmaker.entity.FollowEntity;
import com.travelmaker.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowRepository extends JpaRepository<FollowEntity, String> {

    @Query(value = "Select * from user where userId in " +
            "(select following from follow where user_id = :id)", nativeQuery = true)
    List<UserEntity> followingList(@Param("id") String id);

    @Query(value = "Select * from user where userId in " +
            "(select user_id from follow where following = :id)", nativeQuery = true)
    List<UserEntity> followerList(@Param("id") String id);
}
