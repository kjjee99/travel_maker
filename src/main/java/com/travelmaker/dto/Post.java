package com.travelmaker.dto;

import com.travelmaker.entity.PostEntity;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 게시글 관련 DTO
 */

@Data
@NoArgsConstructor
public class Post {
    /** POST INDEX */
    private int idx;

    /** 사용자 ID */
    private String userId;

    /** 글 제목 */
    private String title;

    /** 글 내용 */
    private String content;

    /** 좋아요 개수 */
    private int heart;

    /** 추천도 */
    private Figures figures;

    /** 글 사진(array)  */
    private String postImg;

    /** 추천 경로 */
    private List<Roads> recommendRoutes;

    /** 해시태그 */
    private String[] hashtags;


    @Builder
    public Post(int idx, String userId, String title, String content, int heart, Figures figures, String postImg, List<Roads> roads, String[] hashtags) {
        this.idx = idx;
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.heart = heart;
        this.figures = figures;
        this.postImg = postImg;
        this.recommendRoutes = roads;
        this.hashtags = hashtags;
    }

    public Post of(PostEntity post){
        return Post.builder()
                .idx(post.getIdx())
                .userId(post.getUserId())
                .title(post.getTitle())
                .content(post.getContent())
                .heart(post.getHeart())
                .figures(post.getFigures())
                .postImg(post.getPostImg())
                .roads(post.getRoads())
                .build();
    }
}
