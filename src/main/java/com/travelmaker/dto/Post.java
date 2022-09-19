package com.travelmaker.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Data
@NoArgsConstructor
public class Post {
    private int id;
    private int user_id;
    private String title;
    private String content;
    private int like;
    private String figures;
    private String post_img;

    @Builder
    public Post(int id, int user_id, String title, String content, int like, String figures, String post_img) {
        this.id = id;
        this.user_id = user_id;
        this.title = title;
        this.content = content;
        this.like = like;
        this.figures = figures;
        this.post_img = post_img;
    }
}
