package com.travelmaker.repository;

import com.travelmaker.entity.PostnHashtagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface PostnTagRepository extends JpaRepository<PostnHashtagEntity, String> {
    @Transactional
    @Modifying
    @Query(value = "delete from post_hashtag where postid = :idx", nativeQuery = true)
    Optional<Integer> deleteByIdx(@Param("idx") int idx);
}
