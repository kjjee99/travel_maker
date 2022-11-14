package com.travelmaker.controller;

import com.travelmaker.dto.Post;
import com.travelmaker.entity.HashtagEntity;
import com.travelmaker.entity.PostEntity;
import com.travelmaker.error.CustomException;
import com.travelmaker.error.ErrorCode;
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
    public ResponseEntity writePost(@CookieValue("userId") String userId, @RequestBody Post post) {
        post.setUser_id(userId);
        // TODO : 경로 저장
        boolean savedResult = postService.writePost(post);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    /* 글 전체 목록 조회*/
    @Override
    @GetMapping("/list")
    public ResponseEntity<?> postList(@CookieValue("userId") String userId){
        // TODO: Pagination -> 마지막에 보낼 데이터 정하기(더 이상 찾을 수 없을 때)
        if(userId == null) throw new CustomException(ErrorCode.LOGIN_REQUIRED);

        List<PostEntity> list = postService.postList();

        return ResponseEntity.ok(list);
    }

    /* 유저가 작성한 글 목록 조회 */
    @Override
    @GetMapping("/user/list/{id}")
    public ResponseEntity<?> userPostList(@CookieValue("userId") String userId, @PathVariable("id") String id){
        List<PostEntity> list = postService.userPostList(id);

        return ResponseEntity.ok(list);
    }

    /* 글 상세 조회 */
    @Override
    @GetMapping("/detail/{id}")
    public ResponseEntity<?> showPost(@CookieValue("userId") String userId, @PathVariable("id") int idx){
        Post post = postService.showPost(idx);
        return ResponseEntity.ok(post);
    }

    /* 글 수정 */
    @Override
    @PostMapping("/{id}")
    public ResponseEntity<?> modifyPost(@PathVariable("id") int id, @CookieValue("userId") String userId, @RequestBody Post post){
        Post updatedPost = postService.modifyPost(post);

        return ResponseEntity.ok(updatedPost);
    }

    /* 글 삭제 */
    @Override
    @GetMapping("/{id}")
    public ResponseEntity deletePost(@CookieValue("userId") String userId, @PathVariable("id") int id){
        boolean deletedResult = postService.deletePost(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    /* 좋아요 반영 */
    @Override
    @GetMapping("/like")
    public ResponseEntity updateLike(@CookieValue("userId") String userId, @RequestParam int idx, @RequestParam int like){
        // TODO: 사용자가 좋아요한 게시글 저장
        int updatedLike = postService.updateLike(idx, like);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    /* 해시태그 목록 */
    @Override
    @GetMapping("/tag/{keyword}")
    public ResponseEntity searchByKeyword(@PathVariable("keyword") String keyword){
        List<HashtagEntity> list = postService.searchByKeyword(keyword);
        return ResponseEntity.ok(list);
    }


    /* 해시태그로 검색 */
    @Override
    @GetMapping("/tag")
    public ResponseEntity searchByHashtag(@RequestParam String word){
        List<PostEntity> list =  postService.searchByHashtag(word);
        return ResponseEntity.ok(list);
    }
}
