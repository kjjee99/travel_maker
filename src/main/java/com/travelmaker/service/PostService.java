package com.travelmaker.service;

import com.travelmaker.dto.Post;
import com.travelmaker.entity.PostEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface PostService {

    /*글 작성*/
    boolean writePost(Post post);

    /* 글 전체 조회 */
    List<PostEntity> postList();

    /* 사용자가 작성한 글 목록 조회*/

    /* 글 상세조회 */
    Post showPost(int idx);


    /* 글 수정 */
    Post modifyPost(Post post);

    /* 글 삭제 */
    boolean deletePost(int idx);
}
