package com.travelmaker.repository;


import com.travelmaker.dto.User;
import com.travelmaker.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
//@Transactional
public interface UserRepository extends JpaRepository<UserEntity, String> {
    // FIXME: userId, index
    // 유저 검색
    @Query(value = "SELECT * from user u where u.userid = :id", nativeQuery = true)
    Optional<UserEntity> findByUserId(@Param("id") String id);

    // 유저 인덱스(id) 검색
    @Query(value = "SELECT u.id from UserEntity u where u.userid = :id")
    Optional<Integer> findIdByUserId(@Param("id") String id);

    // 회원정보 수정
    @Transactional
    @Modifying
    @Query(value = "UPDATE user u SET u.email = :email, " +
            "u.phonenumber = :phonenumber," +
            "u.profileimg = :profileImg " +
            "where userid = :userId", nativeQuery = true)
    Optional<Integer> updateUser(@Param("userId") String userId,
                          @Param("email") String email,
                          @Param("password") String password,
                          @Param("phonenumber") String phone_number,
                          @Param("profileImg") String profileImg);

    // 비밀번호 수정
    @Transactional
    @Modifying
    @Query(value = "UPDATE user u SET u.password = :pass where userid = :userId", nativeQuery = true)
    Optional<Integer> updatePass(@Param("userId") String userId, @Param("pass") String password);

    // 회원 탈퇴
    @Transactional
    @Modifying
    @Query(value = "UPDATE user u SET u.userid=null, u.email=null, u.password=null," +
            "u.phonenumber=null, u.profileimg=null, u.role=null " +
            "where u.userid = :userId", nativeQuery = true)
    Optional<Integer> deleteByUserId(@Param("userId") String userId);

    // 회원 검색
    // TODO: limit 10
    @Query(value = "select new com.travelmaker.entity.UserEntity(u.idx, u.userid, u.profileimg) from UserEntity u " +
            "where u.userid like concat('%', :word, '%') " +
            "order by u.userid")
    List<UserEntity> findByKeyword(@Param("word") String word);

    // TODO: password 빼고
    // 팔로잉 목록
    @Query(value = "Select * from user where idx in " +
            "(select following from follow where userid = :id)", nativeQuery = true)
    List<UserEntity> followingList(@Param("id") int id);

    // 팔로워 목록
    @Query(value = "Select * from user where idx in " +
            "(select userid from follow where following = :id)", nativeQuery = true)
    List<UserEntity> followerList(@Param("id") int id);
}
