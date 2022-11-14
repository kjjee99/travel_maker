package com.travelmaker.service;

import com.travelmaker.dto.Post;
import com.travelmaker.entity.HashtagEntity;
import com.travelmaker.entity.PostEntity;
import com.travelmaker.entity.PostnHashtagEntity;
import com.travelmaker.error.CustomException;
import com.travelmaker.error.ErrorCode;
import com.travelmaker.repository.HashtagRepository;
import com.travelmaker.repository.PostRepository;
import com.travelmaker.repository.PostnTagRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @Autowired
    private HashtagRepository tagRepository;

    @Autowired
    private PostnTagRepository relationRepository;

    /* 글 작성 */
    @Override
    public boolean writePost(Post post){

        PostEntity entity = PostEntity.builder()
                .user_id(post.getUser_id())
                .title(post.getTitle())
                .content(post.getContent())
                .like(0)
                .figures(post.getFigures())     // 추천도
                // TODO: 여러 개의 이미지 저장
                // TODO: 순서대로 저장
                // TODO: AWS S3에 저장하기
                .post_img(post.getPost_img())
                .roads(post.getRecommendRoutes())
                .createdAt(new Date())
                .build();

        PostEntity savedPost = repository.save(entity);

        // SAVE HASHTAGS
        saveHashtag(savedPost.getId(), post.getHashtags());

        if(savedPost.getTitle().isEmpty())   throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        return true;
    }

    /* 해시태그 저장 */
    @Override
    public void saveHashtag(int postId, String[] hashtags){
        for(String tag : hashtags){
            Optional<HashtagEntity> entity = tagRepository.findByName(tag);
            HashtagEntity findTag;
            if(!entity.isPresent()){
                // SAVE IN Hashtag table
                HashtagEntity tagEntity = HashtagEntity.builder()
                        .tag_name(tag)
                        .build();
                HashtagEntity savedTag = tagRepository.save(tagEntity);
                // ERROR: 저장이 안되었을 경우
                if(savedTag.getTag_name().isEmpty())
                    throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
                findTag = savedTag;
            }
            else findTag = entity.get();
            // SAVE IN Matching table
            PostnHashtagEntity relation = PostnHashtagEntity.builder()
                    .postId(postId)
                    .tagId(findTag.getId())
                    .build();
            relationRepository.save(relation);
        }
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

    /* 글 상세 조회 */
    @Override
    public Post showPost(int idx) {
        Optional<PostEntity> entity = Optional.ofNullable(repository.findByIdx(idx)
                // 찾는 게시글이 없는 경우
                .orElseThrow(() -> new CustomException(ErrorCode.NULL_VALUE)));

        PostEntity post = entity.get();
        Post findPost = Post.builder().id(post.getId())
                .user_id(post.getUser_id())
                .title(post.getTitle())
                .content(post.getContent())
                .like(post.getLike())
                .figures(post.getFigures())
                .post_img(post.getPost_img())
                .roads(post.getRoads())
                .build();

        return findPost;
    }

    /* 글 수정 */
    @Override
    public Post modifyPost(Post post) {
        Optional<PostEntity> entity = Optional.ofNullable(repository.findByIdx(post.getId())
                // 수정할 게시글이 존재하지 않는 경우
                .orElseThrow(() -> new CustomException(ErrorCode.NULL_VALUE)));

        Optional<PostEntity> updatedEntity = Optional.ofNullable(repository.updatePost(post.getId(), post.getTitle(), post.getContent(), post.getFigures().toString(), post.getPost_img())
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
                .roads(post.getRecommendRoutes())
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

    /* Hashtag 목록 검색 */
    @Override
    public List<HashtagEntity> searchByKeyword(String keyword){
        List<HashtagEntity> hashtags = repository.findHashtagsByKeyword(keyword);
        if(hashtags.size() == 0)    throw new CustomException(ErrorCode.NULL_VALUE);

        // 총 10개
        return hashtags.subList(0, 9);
    }

    /* Hashtag 검색 */
    @Override
    public List<PostEntity> searchByHashtag(String word){
        List<PostEntity> postIds = repository.findPostsByKeyword(word);
        // ERROR: 검색한 값이 없을 때
        if(postIds.size() == 0 )    throw new CustomException(ErrorCode.NULL_VALUE);

        return postIds;
    }
}
