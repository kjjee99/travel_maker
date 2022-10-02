package com.travelmaker.controller;

import com.travelmaker.dto.Post;
import com.travelmaker.entity.PostEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface PostController {

    /* 글 작성 */
    ResponseEntity writePost(HttpServletRequest request, @RequestBody Post post);

    /* 글 전체 조회 */
    List<PostEntity> postList(HttpServletRequest request);

    /* 유저가 작성한 글 목록 조회 */
    List<PostEntity> userPostList(HttpServletRequest request);

    /* 글 상세조회 */
    Post showPost(HttpServletRequest request, @RequestParam int idx);

    /* 글 수정 */
    Post modifyPost(HttpServletRequest request,@RequestBody Post post);

    /* 글 삭제 */
    ResponseEntity deletePost(HttpServletRequest request, @RequestParam int idx);
}
