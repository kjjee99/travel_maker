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
    @Column(name = "idx")
    private int idx;

    @Column(name = "userid")
    private String userId;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "`like`")
    private int like;

    @Column(name = "`figures`")
    @Type(type="json")
    private Figures figures;

    @Column(name = "postimg")
    private String postImg;

    @Column(name = "roads")
    @Type(type="json")
    private List<Roads> roads;

    @Column(name = "createdat")
    private Date createdat;

    @Builder
    public PostEntity(int idx, String userId, String title, String content, int like, Figures figures, String postImg, List<Roads> roads, Date createdAt) {
        this.idx = idx;
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.like = like;
        this.figures = figures;
        this.postImg = postImg;
        this.roads = roads;
        this.createdat = createdAt;
    }
}
