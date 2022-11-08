package com.travelmaker.entity;

import com.travelmaker.dto.Figures;
import com.travelmaker.dto.Roads;
import com.vladmihalcea.hibernate.type.json.JsonType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@ToString
@TypeDef(name = "json", typeClass = JsonType.class)
@Table(name = "post")
public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "user_id")
    private String user_id;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "`like`")
    private int like;

    @Column(name = "`figures`")
    @Type(type="json")
    private Figures figures;

    @Column(name = "post_img")
    private String post_img;

    @Column(name = "roads")
    @Type(type="json")
    private List<Roads> roads;

    @Column(name = "created_at")
    private Date created_at;

    @Builder
    public PostEntity(int id, String user_id, String title, String content, int like, Figures figures, String post_img, List<Roads> roads, Date createdAt) {
        this.id = id;
        this.user_id = user_id;
        this.title = title;
        this.content = content;
        this.like = like;
        this.figures = figures;
        this.post_img = post_img;
        this.roads = roads;
        this.created_at = createdAt;
    }
}
