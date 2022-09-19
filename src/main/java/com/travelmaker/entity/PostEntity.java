package com.travelmaker.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor
@ToString
@Table(name = "post")
public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "user_id")
    private int user_id;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "`like`")
    private int like;

    @Column(name = "`figures`")
    private String figures;

    @Column(name = "post_img")
    private String post_img;

    @Column(name = "created_at")
    private Date created_at;

    @Builder
    public PostEntity(int id, int user_id, String title, String content, int like, String figures, String post_img, Date createdAt) {
        this.id = id;
        this.user_id = user_id;
        this.title = title;
        this.content = content;
        this.like = like;
        this.figures = figures;
        this.post_img = post_img;
        this.created_at = createdAt;
    }
}
