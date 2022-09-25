package com.travelmaker.service;

import com.travelmaker.dto.Post;
import com.travelmaker.entity.PostEntity;
import com.travelmaker.repository.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository repository;

    /* 글 작성 */
    @Override
    public boolean writePost(Post post){

        PostEntity entity = PostEntity.builder()
                // TODO: userId를 줄건지 id를 줄건지******
                .user_id(8)
                .title(post.getTitle())
                .content(post.getContent())
                .like(0)
                .figures(post.getFigures())
                // TODO: 여러 개의 이미지 저장
                // TODO: thumbnails
                .post_img(post.getPost_img())
                .createdAt(new Date())
                .build();

        log.info(entity.toString());
        PostEntity savedPost = repository.save(entity);

        if(savedPost == null)   return false;
        return true;
    }

    /* 글 전체 목록 조회 */
    @Override
    public List<PostEntity> postList(){
        List<PostEntity> list = repository.findAll();
        // 삭제된 글을 list에서 삭제
        for(int i = 0; i < list.size(); i++){
            if(list.get(i).getTitle() == null){
                list.remove(i);
                i--;
            }
        }
        return list;
    }

    /* 글 상세 조회*/
    @Override
    public Post showPost(int idx) {
        // TODO: user_id -> string
        Post post = repository.findByIdx(idx);
        return post;
    }

    /* 글 수정 */
    @Override
    public Post modifyPost(Post post) {
        Post findPost = repository.findByIdx(post.getId());
        // TODO: 게시글이 존재하지 않을 때 ERROR
        if(findPost == null){
            return null;
        }
        Post updatedPost = repository.updatePost(post.getId(), post.getTitle(), post.getContent(), post.getFigures(), post.getPost_img());
        // TODO: Update 안됐을 때
        if(updatedPost == null) {return null;}
        return updatedPost;
    }

    /* 글 삭제 */
    @Override
    public boolean deletePost(int idx) {
        Post findPost = repository.findByIdx(idx);
        // TODO: 게시글이 존재하지 않을 때 ERROR
        if(findPost == null)    return false;

        int deletedPost = repository.deletePost(idx);
        // TODO: 게시글이 삭제되지 않았을 때 ERROR
        if(deletedPost == -1)   return false;
        return true;
    }
}
