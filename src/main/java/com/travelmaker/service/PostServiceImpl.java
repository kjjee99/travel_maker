package com.travelmaker.service;

import com.travelmaker.dto.Post;
import com.travelmaker.entity.PostEntity;
import com.travelmaker.error.CustomException;
import com.travelmaker.error.ErrorCode;
import com.travelmaker.repository.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository repository;

    /* 글 작성 */
    @Override
    public boolean writePost(Post post){

        PostEntity entity = PostEntity.builder()
                // TODO: userId로 바꾸기
                .user_id(8)
                .title(post.getTitle())
                .content(post.getContent())
                .like(0)
                .figures(post.getFigures())     // 추천도
                // TODO: 여러 개의 이미지 저장
                // TODO: 순서대로 저장
                // TODO: AWS S3에 저장하기
                .post_img(post.getPost_img())
                .createdAt(new Date())
                .build();

        log.info(entity.toString());
        PostEntity savedPost = repository.save(entity);

        if(savedPost.getTitle().isEmpty())   throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        return true;
    }

    /* 글 전체 목록 조회 */
    @Override
    // TODO: 팔로우한 유저만 게시글 뜨기
    public List<PostEntity> postList(){
        List<PostEntity> list = repository.findAll();
        // 저장된 게시글이 없는 경우
        if(list.size() == 0)    throw new CustomException(ErrorCode.NULL_VALUE);

        // 삭제된 글을 list에서 삭제
        for(int i = 0; i < list.size(); i++){
            if(list.get(i).getTitle() == null){
                list.remove(i);
                i--;
            }
        }
        return list;
    }

    /* 유저가 작성한 글 목록 조회 */
    @Override
    public List<PostEntity> userPostList(String id){
        List<PostEntity> list = repository.findByUserId(id);
        // 저장된 게시글이 없는 경우
        if(list.size() == 0)    throw new CustomException(ErrorCode.NULL_VALUE);

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
        Optional<PostEntity> entity = Optional.ofNullable(repository.findByIdx(idx)
                // 찾는 게시글이 없는 경우
                .orElseThrow(() -> new CustomException(ErrorCode.NULL_VALUE)));

        PostEntity post = entity.get();
        Post findPost = Post.builder().id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .like(post.getLike())
                .figures(post.getFigures())
                .post_img(post.getPost_img())
                .build();

        return findPost;
    }

    /* 글 수정 */
    @Override
    public Post modifyPost(Post post) {
        Optional<PostEntity> entity = Optional.ofNullable(repository.findByIdx(post.getId())
                // 수정할 게시글이 존재하지 않는 경우
                .orElseThrow(() -> new CustomException(ErrorCode.NULL_VALUE)));

        Optional<PostEntity> updatedEntity = Optional.ofNullable(repository.updatePost(post.getId(), post.getTitle(), post.getContent(), post.getFigures(), post.getPost_img())
                // 수정 시 오류가 발생한 경우
                .orElseThrow(() -> new CustomException(ErrorCode.INTERNAL_SERVER_ERROR)));

        Post updatedPost = Post.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .like(post.getLike())
                .figures(post.getFigures())
                // 주소로 변환된 값 반환
                .post_img(updatedEntity.get().getPost_img())
                .build();

        return updatedPost;
    }

    /* 글 삭제 */
    @Override
    public boolean deletePost(int idx) {
        Optional<PostEntity> findPost = Optional.ofNullable(repository.findByIdx(idx)
                // 삭제할 게시글이 없는 경우
                .orElseThrow(() -> new CustomException(ErrorCode.NULL_VALUE)));

        Optional<Integer> deletedPost = Optional.ofNullable(repository.deletePost(idx)
                // 삭제 시 오류가 발생한 경우
                .orElseThrow(() -> new CustomException(ErrorCode.INTERNAL_SERVER_ERROR)));
        return true;
    }

    /* 좋아요 반영 */
    @Override
    public int updateLike(int idx, int like){
        Optional<Integer> entity = Optional.ofNullable(repository.updateLike(idx, like)
                .orElseThrow(() -> new CustomException(ErrorCode.INTERNAL_SERVER_ERROR)) );
        return entity.get();
    }
}
