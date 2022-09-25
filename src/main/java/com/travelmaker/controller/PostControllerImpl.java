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

        // TODO: ERROR
        if(!savedResult)    return ResponseEntity.ok(HttpStatus.FORBIDDEN);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    /* 글 전체 목록 조회*/
    @Override
    @GetMapping("/list")
    public List<PostEntity> postList(HttpServletRequest request){
        String userId = middleware.extractId(request);
        // TODO: Login Required Error
        if(userId == null)  return null;

        List<PostEntity> list = postService.postList();

        // TODO: 게시글이 존재하지 않을 때 ERROR
        if(list.size() == 0)    return null;
        return list;
    }

    /* 글 상세 조회 */
    public Post showPost(HttpServletRequest request, @RequestParam int idx){
        String userId = middleware.extractId(request);
        // TODO: Login Required Error
        if(userId == null)  return null;

        Post post = postService.showPost(idx);
        return post;
    }

    /* 글 수정 */
    public Post modifyPost(HttpServletRequest request, @RequestBody Post post){
        String userId = middleware.extractId(request);
        // TODO: Login Required Error
        if(userId == null)  return null;

        Post updatedPost = postService.modifyPost(post);
        return updatedPost;
    }

    /* 글 삭제 */
    public ResponseEntity deletePost(@RequestParam int idx){
        boolean deletedResult = postService.deletePost(idx);
        // TODO: ERROR
        if(!deletedResult)  return ResponseEntity.ok(HttpStatus.FORBIDDEN);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
