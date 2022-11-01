package com.travelmaker.controller;

import com.travelmaker.dto.Post;
import com.travelmaker.entity.PostEntity;
import com.travelmaker.service.PostServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/post")
@Slf4j
public class PostControllerImpl implements PostController{

    @Autowired
    PostServiceImpl postService;

    /* 글 작성 */
    @Override
    @PostMapping("/write")
    public ResponseEntity writePost(HttpServletRequest request, @CookieValue("userId") String userId, @RequestBody Post post) {
        post.setUser_id(userId);
        boolean savedResult = postService.writePost(post);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    /* 글 전체 목록 조회*/
    @Override
    @GetMapping("/list")
    public ResponseEntity<?> postList(HttpServletRequest request, @CookieValue("userId") String userId){
        List<PostEntity> list = postService.postList();

        return ResponseEntity.ok(list);
    }

    /* 유저가 작성한 글 목록 조회 */
    @Override
    @GetMapping("/user/list")
    public ResponseEntity<?> userPostList(HttpServletRequest request, @CookieValue("userId") String userId){
        List<PostEntity> list = postService.userPostList(userId);

        return ResponseEntity.ok(list);
    }

    /* 글 상세 조회 */
    @Override
    @GetMapping("/detail/{id}")
    public ResponseEntity<?> showPost(HttpServletRequest request, @CookieValue("userId") String userId, @PathVariable("id") int idx){
        Post post = postService.showPost(idx);
        return ResponseEntity.ok(post);
    }

    /* 글 수정 */
    @Override
    @PostMapping("/")
    public ResponseEntity<?> modifyPost(HttpServletRequest request, @CookieValue("userId") String userId, @RequestBody Post post){
        Post updatedPost = postService.modifyPost(post);

        return ResponseEntity.ok(updatedPost);
    }

    /* 글 삭제 */
    @Override
    @GetMapping("/")
    public ResponseEntity deletePost(HttpServletRequest request, @CookieValue("userId") String userId, @RequestParam int idx){
        boolean deletedResult = postService.deletePost(idx);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    /* 좋아요 반영 */
    @Override
    @GetMapping("/like")
    public int updateLike(HttpServletRequest request, @CookieValue("userId") String userId, @RequestParam int idx, @RequestParam int like){
        // TODO: 사용자가 좋아요한 게시글 저장
        int updatedLike = postService.updateLike(idx, like);
        return updatedLike;
    }

    /* 검색 */
    @Override
    @GetMapping("/tag")
    public List<PostEntity> searchByKeyword(HttpServletRequest request,@CookieValue("userId") String userId, @RequestParam String word){
        return postService.searchByKeyword(word);
    }
}
