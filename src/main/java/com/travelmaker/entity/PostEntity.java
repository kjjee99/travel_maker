package com.travelmaker.entity;

import com.travelmaker.dto.Figures;
import com.travelmaker.dto.Roads;
import com.vladmihalcea.hibernate.type.json.JsonType;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@ToString
@TypeDef(name = "json", typeClass = JsonType.class)
@Table(name = "post")
public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idx;

    @Column(name = "userid", nullable = false)
    private String userId;

    private String title;

    private String content;

    private int heart;

    @Column(name = "`figures`", nullable = false)
    @Type(type="json")
    private Figures figures;

    @Column(name = "postimg", nullable = false)
    private String postImg;

    @Type(type="json")
    private List<Roads> roads;

    @Column(name = "createdat", nullable = false)
    private Date createdAt;

    @Builder
    public PostEntity(int idx, String userId, String title, String content, int heart, Figures figures, String postImg, List<Roads> roads, Date createdAt) {
        this.idx = idx;
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.heart = heart;
        this.figures = figures;
        this.postImg = postImg;
        this.roads = roads;
        this.createdAt = createdAt;
    }
}
