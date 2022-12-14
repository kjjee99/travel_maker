package com.travelmaker.repository;

import com.travelmaker.entity.HashtagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface HashtagRepository extends JpaRepository<HashtagEntity, String> {

    // 해시태그 검색
    @Query(value = "select * from hashtags where tagname = :name",
            nativeQuery = true)
    Optional<HashtagEntity> findByName(@Param("name")String name);

    // 해시태그로 게시글 검색
    @Query(value = "select tagname from hashtags h where idx in " +
            "(select tagid from post_hashtag where postid = :idx)", nativeQuery = true)
    String[] findTagsByPost(@Param("idx") int idx);

    // 해시태그 목록
    @Query(value = "select tagname from hashtags where tagname like concat('%', :name, '%')", nativeQuery=true)
    Optional<String[]> findHashtagsByKeyword(@Param("name") String name);
}
