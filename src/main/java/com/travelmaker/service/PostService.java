package com.travelmaker.service;

import com.travelmaker.dto.Post;
import com.travelmaker.entity.HashtagEntity;
import com.travelmaker.entity.PostEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostService {

    /*글 작성*/
    boolean writePost(Post post, List<MultipartFile> images);

    /* 해시태그 저장 */
    void saveHashtag(int postId, String[] hashtags);

    /* 글 전체 조회 */
    List<Post> postList(String userId, int pageNumber);

    /* 유저가 작성한 글 목록 조회 */
    List<PostEntity> userPostList(String id);

    /* 글 상세조회 */
    Post showPost(int idx);

    /* 글 수정 */
    Post modifyPost(Post post, List<MultipartFile> images);

    /* 글 삭제 */
    boolean deletePost(int idx);

    /* 좋아요 확인 */
    void checkLike(int idx, String userId);

    /* 좋아요 반영 */
    void updateLike(int idx, String userId);

    /* 좋아요 취소 */
    void cancelLike(int idx, String userId);

    /* Hashtag 목록 검색 */
    String[] searchByKeyword(String keyword);

    /* Hashtag 검색 */
    List<PostEntity> searchByHashtag(String word, int pageNumber);
}
