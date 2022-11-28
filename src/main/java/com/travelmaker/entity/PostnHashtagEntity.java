package com.travelmaker.entity;

import com.travelmaker.dto.PostnHashtag;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
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
