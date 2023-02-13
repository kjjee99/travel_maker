package com.travelmaker.entity;

import com.travelmaker.dto.PostnHashtag;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "post_hashtag")
@IdClass(PostnHashtag.class)
public class PostnHashtagEntity implements Serializable {
    @Id
    @Column(name = "postid")
    private int postId;
    @Id
    @Column(name = "tagid")
    private int tagId;

    @Builder
    public PostnHashtagEntity(int postId, int tagId) {
        this.postId = postId;
        this.tagId = tagId;
    }
}
