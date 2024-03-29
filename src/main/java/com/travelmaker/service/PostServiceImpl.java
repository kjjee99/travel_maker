package com.travelmaker.service;

import com.travelmaker.config.AmazonS3ResourceStorage;
import com.travelmaker.dto.FileDetail;
import com.travelmaker.dto.Post;
import com.travelmaker.dto.PostUpdate;
import com.travelmaker.entity.HashtagEntity;
import com.travelmaker.entity.HeartEntity;
import com.travelmaker.entity.PostEntity;
import com.travelmaker.entity.PostnHashtagEntity;
import com.travelmaker.error.CustomException;
import com.travelmaker.error.ErrorCode;
import com.travelmaker.repository.HashtagRepository;
import com.travelmaker.repository.HeartRepository;
import com.travelmaker.repository.PostRepository;
import com.travelmaker.repository.PostnTagRepository;
import com.travelmaker.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Slf4j
@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository repository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HashtagRepository tagRepository;

    @Autowired
    private PostnTagRepository relationRepository;

    @Autowired
    private HeartRepository heartRepository;

    @Autowired
    private AmazonS3ResourceStorage amazonS3ResourceStorage;

    /* 글 작성 */
    @Override
    public boolean writePost(Post post, List<MultipartFile> images){

        String imageUrl = "";

        // TODO: 1MB 이상 용량 확인
        int len = images.size() <= 10 ? images.size() : 10;

        for(int i = 0; i < len; i++) {
            FileDetail fileDetail = FileDetail.multipartOf(images.get(i));
            String storedImg = amazonS3ResourceStorage.store(fileDetail.getPath(), images.get(i), fileDetail.getId());
            // 쉼표(,)로 split && 이미지 이름만 저장
            imageUrl += storedImg.split("/")[4] + ",";
        }

        PostEntity entity = PostEntity.builder()
                .userId(post.getUserId())
                .title(post.getTitle())
                .content(post.getContent())
                .heart(0)
                .figures(post.getFigures())     // 추천도
                .postImg(imageUrl)
                .roads(post.getRecommendRoutes())
                .createdAt(new Date())
                .build();

        PostEntity savedPost = repository.save(entity);

        // SAVE HASHTAGS
        saveHashtag(savedPost.getIdx(), post.getHashtags());

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
                        .tagName(tag)
                        .build();
                HashtagEntity savedTag = tagRepository.save(tagEntity);
                // ERROR: 저장이 안되었을 경우
                if(savedTag.getTagName().isEmpty())
                    throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
                findTag = savedTag;
            }
            else findTag = entity.get();
            // SAVE IN Matching table
            PostnHashtagEntity relation = PostnHashtagEntity.builder()
                    .postId(postId)
                    .tagId(findTag.getIdx())
                    .build();
            relationRepository.save(relation);
        }
    }

    /* 글 전체 목록 조회 */
    @Override
    public List<Post> postList(String userId, int pageNumber){
        PageRequest pageRequest = PageRequest.of(pageNumber, 9);
        int idx = userRepository.findIdByUserId(userId).get();
        List<PostEntity> list = repository.findByFollowing(idx, pageRequest);

        // 저장된 게시글이 없는 경우
        if(list.size() == 0)    throw new CustomException(ErrorCode.NULL_VALUE);

        // 삭제된 글을 list에서 삭제
        for(int i = 0; i < list.size(); i++){
            if(list.get(i).getTitle() == null){
                list.remove(i);
                i--;
            }
        }

        List<Post> posts = new ArrayList<>();
        for(PostEntity entity : list){
            String[] tag = tagRepository.findTagsByPost(entity.getIdx());
            Post post = Post.builder().idx(entity.getIdx())
                    .userId(entity.getUserId())
                    .postImg(entity.getPostImg())
                    .title(entity.getTitle())
                    .content(entity.getContent())
                    .heart(entity.getHeart())
                    .figures(entity.getFigures())
                    .roads(entity.getRoads())
                    .hashtags(tag).build();
            posts.add(post);
        }
        return posts;
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
        Post findPost = Post.builder().idx(post.getIdx())
                .userId(post.getUserId())
                .title(post.getTitle())
                .content(post.getContent())
                .heart(post.getHeart())
                .figures(post.getFigures())
                .postImg(post.getPostImg())
                .roads(post.getRoads())
                .build();

        return findPost;
    }

    /* 글 수정 */
    @Override
    public Post modifyPost(PostUpdate post, List<MultipartFile> images) {
        PostEntity entity = repository.findByIdx(post.getIdx())
                // 수정할 게시글이 존재하지 않는 경우
                .orElseThrow(() -> new CustomException(ErrorCode.NULL_VALUE));

        // 받아온 이미지 링크 중 삭제된 이미지를 저장소에서 삭제
        String[] storedImages = entity.getPostImg().split(",");   // DB에 저장된 이미지 링크
        if(storedImages.length > post.getPostImg().length){
            for(int j = 0; j < post.getPostImg().length; j++){
                for(int i = j; i < storedImages.length; i++) {
                    if (storedImages[i].equals(post.getPostImg()[j])) break;
                    amazonS3ResourceStorage.deleteFile(storedImages[i]);
                }
            }
        }

        String imageUrl = Arrays.toString(post.getPostImg()).replace(" ", "")
                .replace("[", "").replace("]","") + ",";
        log.info(imageUrl);
        if(images != null) {   // 이미지가 수정되었을 때
            // 새로 등록된 이미지 등록
            for (int i = 0; i < images.size(); i++) {
                FileDetail fileDetail = FileDetail.multipartOf(images.get(i));
                String storedImg = amazonS3ResourceStorage.store(fileDetail.getPath(), images.get(i), fileDetail.getId());
                // 쉼표(,)로 split
                imageUrl += storedImg.split("/")[4] + ",";
            }
        }

        // TODO: 해시태그 수정

        entity.setTitle(post.getTitle());
        entity.setContent(post.getContent());
        entity.setPostImg(imageUrl);
        entity.setFigures(post.getFigures());
        entity.setRoads(post.getRecommendRoutes());

        PostEntity updatedPost = repository.save(entity);
        return new Post().of(updatedPost);
    }

    /* 글 삭제 */
    @Override
    public boolean deletePost(int idx) {
        Optional<PostEntity> findPost = Optional.ofNullable(repository.findByIdx(idx)
                // 삭제할 게시글이 없는 경우
                .orElseThrow(() -> new CustomException(ErrorCode.NULL_VALUE)));

        Optional<Integer> deletedPost = Optional.ofNullable(repository.deletePostByIdx(idx)
                // 삭제 시 오류가 발생한 경우
                .orElseThrow(() -> new CustomException(ErrorCode.INTERNAL_SERVER_ERROR)));

        // 게시글 삭제 시 해시태그 삭제
        // TODO: 해시태그된 게시글이 모두 삭제되었을 경우 DB에서 삭제
        relationRepository.deleteByIdx(idx)
                .orElseThrow(() -> new CustomException(ErrorCode.INTERNAL_SERVER_ERROR));
        return true;
    }

    /* 좋아요 확인 */
    @Override
    public void checkLike(int idx, String userId){
        Optional<HeartEntity> heart = heartRepository.findHeartByUserAndPost(userId, idx);
        // ERROR: 이미 하트가 눌러져있는 경우 에러 발생
        if(heart.isPresent()) throw new CustomException(ErrorCode.IS_HEARTED);
    }

    /* 좋아요 반영 */
    @Override
    public void updateLike(int idx, String userId){
        // 좋아요가 눌려있는지 확인
        checkLike(idx, userId);

        Optional<Integer> entity = Optional.ofNullable(repository.updateLike(idx)
                .orElseThrow(() -> new CustomException(ErrorCode.INTERNAL_SERVER_ERROR)));

        HeartEntity heartEntity = HeartEntity.builder().userId(userId).postIdx(idx).build();
        heartRepository.save(heartEntity);
    }

    /* 좋아요 취소 */
    @Override
    public void cancelLike(int idx, String userId){
        // 좋아요 기록 삭제
        Optional<Integer> heart = Optional.ofNullable(heartRepository.unlike(userId, idx)
                .orElseThrow(() -> new CustomException(ErrorCode.INTERNAL_SERVER_ERROR)));

        Optional<Integer> entity = Optional.ofNullable(repository.cancelLike(idx)
                .orElseThrow(()->new CustomException(ErrorCode.INTERNAL_SERVER_ERROR)));
    }

    /* Hashtag 목록 검색 */
    @Override
    public String[] searchByKeyword(String keyword){
        Optional<String[]> hashtags = Optional.ofNullable(tagRepository.findHashtagsByKeyword(keyword)
                .orElseThrow(() -> new CustomException(ErrorCode.NULL_VALUE)));

        String[] list = hashtags.get();
        // ERROR: 빈 배열일 때 에러 발생
        if(list.length == 0)    throw new CustomException(ErrorCode.NULL_VALUE);

        int size = list.length > 9 ? 10 : list.length;
        // 총 10개만 출력
        return Arrays.copyOfRange(hashtags.get(), 0, size);
    }

    /* Hashtag 검색 */
    @Override
    public List<PostEntity> searchByHashtag(String word, int pageNumber){
        PageRequest pageRequest = PageRequest.of(pageNumber, 9);
        List<PostEntity> postIds = repository.findPostsByKeyword(word, pageRequest);
        // ERROR: 검색한 값이 없을 때
        if(postIds.size() == 0 )    throw new CustomException(ErrorCode.NULL_VALUE);

        return postIds;
    }
}
