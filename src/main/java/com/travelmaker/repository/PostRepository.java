package com.travelmaker.repository;

import com.travelmaker.dto.Post;
import com.travelmaker.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, String> {

    @Query(value="select user_id, title, content, like, figures, post_img, create_at" +
            "from post", nativeQuery = true)
    Post findByIdx(int idx);

    @Modifying
    @Query(value="update post set title=:title, content= :content, figures= :figures, post_img = :postImg" +
            "where id=:id", nativeQuery = true)
    Post updatePost(@Param("id") int id,
                    @Param("title") String title,
                    @Param("content") String content,
                    @Param("figures") String figures,
                    @Param("postImg") String postImg);

    @Modifying
    @Query(value = "update post set title=null, content=null, figures=null, post_img=null" +
            "where id=:id", nativeQuery = true)
    int deletePost(@Param("id") int idx);
}
