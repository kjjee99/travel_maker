package com.travelmaker.controller;

import com.travelmaker.dto.Post;
import com.travelmaker.entity.PostEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface PostController {

    /* 글 작성 */
    ResponseEntity writePost(HttpServletRequest request, @CookieValue("userId") String userId, @RequestBody Post post);

    /* 글 전체 조회 */
    ResponseEntity postList(HttpServletRequest request, @CookieValue("userId") String userId);

    /* 유저가 작성한 글 목록 조회 */
    ResponseEntity userPostList(HttpServletRequest request, @CookieValue("userId") String userId);

    /* 글 상세조회 */
    ResponseEntity showPost(HttpServletRequest request, @CookieValue("userId") String userId, @PathVariable int idx);

    /* 글 수정 */
    ResponseEntity modifyPost(HttpServletRequest request, @CookieValue("userId") String userId, @RequestBody Post post);

    /* 글 삭제 */
    ResponseEntity deletePost(HttpServletRequest request, @CookieValue("userId") String userId, @RequestParam int idx);

    /* 좋아요 반영 */
    int updateLike(HttpServletRequest request, @CookieValue("userId") String userId, @RequestParam int idx, @RequestParam int like);

    /* 검색 */
    List<PostEntity> searchByKeyword(HttpServletRequest request, @CookieValue("userId") String userId, @RequestParam String word);
}
