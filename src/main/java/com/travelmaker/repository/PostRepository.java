package com.travelmaker.repository;

import com.travelmaker.entity.HashtagEntity;
import com.travelmaker.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, String> {

    // 상세조회
    @Query(value="select * from post where idx=:idx", nativeQuery = true)
    Optional<PostEntity> findByIdx(@Param("idx") int idx);    // 인덱스 번호

    // FIXME: index, title, img
    // 유저가 작성한 글 조회
    @Query(value = "select * from post where userid = :id", nativeQuery = true)
    List<PostEntity> findByUserId(@Param("id") String id);

    // 수정
    @Transactional
    @Modifying
    @Query(value="update post set title=:title, content= :content, figures= :figures, postimg = :postImg" +
            "where idx=:idx", nativeQuery = true)
    Optional<PostEntity> updatePost(@Param("idx") int id,    // userId
                    @Param("title") String title,
                    @Param("content") String content,
                    @Param("figures") String figures,
                    @Param("postImg") String postImg);

    // 삭제
    @Transactional
    @Modifying
    @Query(value = "delete from post where idx=:idx", nativeQuery = true)
    Optional<Integer> deletePost(@Param("idx") int id);

    // 좋아요 반영
    @Transactional
    @Modifying
    @Query(value = "update post set `like`=:like where idx=:idx", nativeQuery = true)
    Optional<Integer> updateLike(@Param("idx") int idx, @Param("like") int like);



    // 해시태그로 검색
    @Query(value = "select * from post where idx = " +
            "(select postid from post_hashtag where tagid is " +
            "select idx from hashtags where tagname is :name)", nativeQuery = true)
    List<PostEntity> findPostsByKeyword(@Param("name") String name);

}
