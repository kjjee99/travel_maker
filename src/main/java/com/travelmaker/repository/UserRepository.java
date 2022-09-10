package com.travelmaker.repository;


import com.travelmaker.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {
    @Query(value = "SELECT * from user u where u.user_id = :id", nativeQuery = true)
    UserEntity findByUserId(@Param("id") String id);

}
