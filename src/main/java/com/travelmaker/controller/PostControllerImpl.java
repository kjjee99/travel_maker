package com.travelmaker.controller;

import com.travelmaker.dto.Post;
import com.travelmaker.entity.PostEntity;
import com.travelmaker.service.PostServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/post")
public class PostControllerImpl implements PostController{

    @Autowired
    PostServiceImpl postService;
    @Autowired
    ConfigControllerImpl middleware;

    /* 글 작성 */
    @Override
    @PostMapping("/write")
    public ResponseEntity writePost(HttpServletRequest request, @RequestBody Post post) {
        String userId = middleware.extractId(request);
        // TODO: Login Required Error
        if(userId == null)  return ResponseEntity.ok(HttpStatus.FORBIDDEN);

        boolean savedResult = postService.writePost(post);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    /* 글 전체 목록 조회*/
    @Override
    @GetMapping("/list")
    public List<PostEntity> postList(HttpServletRequest request){
        String userId = middleware.extractId(request);

        List<PostEntity> list = postService.postList();

        return list;
    }

    /* 글 상세 조회 */
    @Override
    @GetMapping("/detail")
    public Post showPost(HttpServletRequest request, @RequestParam int idx){
        String userId = middleware.extractId(request);

        Post post = postService.showPost(idx);
        return post;
    }

    /* 글 수정 */
    @Override
    @PostMapping("/post")
    public Post modifyPost(HttpServletRequest request, @RequestBody Post post){
        String userId = middleware.extractId(request);

        // TODO: 작성한 유저인지 확인하기
        Post updatedPost = postService.modifyPost(post);
        return updatedPost;
    }

    /* 글 삭제 */
    @Override
    @GetMapping("/post")
    public ResponseEntity deletePost(@RequestParam int idx){
        // TODO: 작성한 유저인지 확인하기
        boolean deletedResult = postService.deletePost(idx);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
