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

        post.setUser_id(userId);
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

    /* 유저가 작성한 글 목록 조회 */
    @Override
    @GetMapping("/user/list")
    public List<PostEntity> userPostList(HttpServletRequest request){
        String userId = middleware.extractId(request);

        List<PostEntity> list = postService.userPostList(userId);

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

        Post updatedPost = postService.modifyPost(post);

        return updatedPost;
    }

    /* 글 삭제 */
    @Override
    @GetMapping("/post")
    public ResponseEntity deletePost(HttpServletRequest request, @RequestParam int idx){
        String userId = middleware.extractId(request);
        boolean deletedResult = postService.deletePost(idx);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    /* 좋아요 반영 */
    @Override
    @GetMapping("/like")
    public int updateLike(HttpServletRequest request, @RequestParam int idx, @RequestParam int like){
        String userId = middleware.extractId(request);
        // TODO: 사용자가 좋아요한 게시글 저장
        int updatedLike = postService.updateLike(idx, like);
        return updatedLike;
    }

    /* 검색 */
    @Override
    @GetMapping("/search")
    public List<PostEntity> searchByKeyword(HttpServletRequest request,@RequestParam String word){
        middleware.extractId(request);

        return postService.searchByKeyword(word);
    }
}
