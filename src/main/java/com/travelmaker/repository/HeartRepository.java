package com.travelmaker.repository;

import com.travelmaker.entity.HeartEntity;
import com.travelmaker.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface HeartRepository extends JpaRepository<HeartEntity, String> {

    // 좋아요 취소
    @Transactional
    @Modifying
    @Query(value = "delete from heart where userid=:userId and postidx=:postIdx", nativeQuery = true)
    Optional<Integer> unlike(String userId, int postIdx);

    // 좋아요 확인
    @Query(value = "select * from heart where userid=:userId and postidx=:postIdx", nativeQuery = true)
    Optional<HeartEntity> findHeartByUserAndPost(@Param("userId") String userId, @Param("postIdx") int postIdx);
}
