package com.travelmaker.repository;


import com.travelmaker.dto.User;
import com.travelmaker.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
//@Transactional
public interface UserRepository extends JpaRepository<UserEntity, String> {
    @Query(value = "SELECT * from user u where u.user_id = :id", nativeQuery = true)
    Optional<UserEntity> findByUserId(@Param("id") String id);

    @Modifying
    @Query(value = "UPDATE user u SET u.user_id = :userId, " +
            "u.email = :email, u.password = :password, " +
            "u.phone_number = :phone_number," +
            "u.profile_img = :profileImg, u.role = :role " +
            "where user_id = :userId", nativeQuery = true)
    Optional<Integer> updateUser(@Param("userId") String userId,
                          @Param("email") String email,
                          @Param("password") String password,
                          @Param("phone_number") String phone_number,
                          @Param("profileImg") String profileImg,
                          @Param("role") String role);

    @Modifying
    @Query(value = "UPDATE user u SET u.user_id=null, u.email=null, u.password=null," +
            "u.phone_number=null, u.profile_img=null, u.role=null " +
            "where u.user_id = :userId", nativeQuery = true)
    Optional<Integer> deleteByUserId(@Param("userId") String userId);
}
