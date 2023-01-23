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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/post")
@Slf4j
public class PostControllerImpl{

    @Autowired
    PostServiceImpl postService;

    /* 글 작성 */
    @PostMapping(value="/write")
    public ResponseEntity writePost(@CookieValue("userId") String userId,
                                    @RequestPart("post") Post post,
                                    @RequestPart("images") List<MultipartFile> images) {
        post.setUserId(userId);
        boolean savedResult = postService.writePost(post, images);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    /* 글 전체 목록 조회*/
    @GetMapping("/list")
    public ResponseEntity<?> postList(@CookieValue("userId") String userId){
        // TODO: Pagination -> 마지막에 보낼 데이터 정하기(더 이상 찾을 수 없을 때)

        List<Post> list = postService.postList();

        return ResponseEntity.ok(list);
    }

    /* 유저가 작성한 글 목록 조회 */
    @GetMapping("/user/list/{id}")
    public ResponseEntity<?> userPostList(@CookieValue("userId") String userId, @PathVariable("id") String id){
        List<PostEntity> list = postService.userPostList(id);

        return ResponseEntity.ok(list);
    }

    /* 글 상세 조회 */
    @GetMapping("/detail/{id}")
    public ResponseEntity<?> showPost(@CookieValue("userId") String userId, @PathVariable("id") int idx){
        Post post = postService.showPost(idx);
        return ResponseEntity.ok(post);
    }

    /* 글 수정 */
    @PostMapping("/{idx}")
    public ResponseEntity<?> modifyPost(@PathVariable("idx") int idx, @CookieValue("userId") String userId,
                                        @RequestPart("post") Post post,
                                        @RequestPart("images") List<MultipartFile> images){
        post.setIdx(idx);
        Post updatedPost = postService.modifyPost(post, images);

        return ResponseEntity.ok(updatedPost);
    }

    /* 글 삭제 */
    @GetMapping("/{id}")
    public ResponseEntity deletePost(@CookieValue("userId") String userId, @PathVariable("id") int id){
        // TODO: 글 삭제하는 사람과 일치하는지 확인
        boolean deletedResult = postService.deletePost(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    /* 좋아요 확인 */
    @GetMapping("/check/like")
    public ResponseEntity checkLike(@CookieValue("userId") String userId, @RequestParam int idx){
        postService.checkLike(idx, userId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    /* 좋아요 반영 */
    @GetMapping("/like")
    public ResponseEntity updateLike(@CookieValue("userId") String userId, @RequestParam int idx){
        postService.updateLike(idx, userId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    /* 좋아요 취소 */
    @GetMapping("/unlike")
    public ResponseEntity cancelLike(@CookieValue("userId") String userId, @RequestParam int idx){
        postService.cancelLike(idx, userId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    /* 해시태그 목록 */
    @GetMapping("/tag/{keyword}")
    public ResponseEntity searchByKeyword(@PathVariable("keyword") String keyword){
        String[] list = postService.searchByKeyword(keyword);
        return ResponseEntity.ok(list);
    }


    /* 해시태그로 검색 */
    @GetMapping("/tag")
    public ResponseEntity searchByHashtag(@RequestParam String word, @RequestParam int page){
        List<PostEntity> list =  postService.searchByHashtag(word, page);
        return ResponseEntity.ok(list);
    }
}
