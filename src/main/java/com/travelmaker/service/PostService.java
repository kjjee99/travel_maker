package com.travelmaker.service;

import com.travelmaker.dto.Post;
import com.travelmaker.entity.HashtagEntity;
import com.travelmaker.entity.PostEntity;
import java.util.List;

public interface PostService {

    /*글 작성*/
    boolean writePost(Post post);

    /* 해시태그 저장 */
    void saveHashtag(int postId, String[] hashtags);

    /* 글 전체 조회 */
    List<Post> postList();

    /* 유저가 작성한 글 목록 조회 */
    List<PostEntity> userPostList(String id);

    /* 글 상세조회 */
    Post showPost(int idx);

    /* 글 수정 */
    Post modifyPost(Post post);

    /* 글 삭제 */
    boolean deletePost(int idx);

    /* 좋아요 반영 */
    int updateLike(int idx, int like);

    /* Hashtag 목록 검색 */
    List<HashtagEntity> searchByKeyword(String keyword);

    /* Hashtag 검색 */
    List<PostEntity> searchByHashtag(String word);
}
