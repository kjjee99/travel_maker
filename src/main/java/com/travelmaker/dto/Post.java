package com.travelmaker.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 게시글 관련 DTO
 */

@Data
@NoArgsConstructor
public class Post {
    // TODO: 주석달기🌟
    /** POST INDEX */
    private int id;
    /** 사용자 ID */
    private String user_id;
    /** 글 제목 */
    private String title;
    /** 글 내용 */
    private String content;
    /** 좋아요 개수 */
    private int like;
    /** 추천도 */
    private String figures;
    /** 글 사진(array)  */
    private String post_img;
    /** 해시태그 */
    private String[] hashtags;

    @Builder
    public Post(int id, String user_id, String title, String content, int like, String figures, String post_img) {
        this.id = id;
        this.user_id = user_id;
        this.title = title;
        this.content = content;
        this.like = like;
        this.figures = figures;
        this.post_img = post_img;
    }

    @Builder
    public Post(int id, String user_id, String title, String content, int like, String figures, String post_img, String[] hashtags) {
        this.id = id;
        this.user_id = user_id;
        this.title = title;
        this.content = content;
        this.like = like;
        this.figures = figures;
        this.post_img = post_img;
        this.hashtags = hashtags;
    }
}
