package com.travelmaker.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "follow")
public class FollowEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idx")
    private int idx;
    @Column(name = "userid")
    private int userId;
    @Column(name = "following")
    private int following;

    @Builder
    public FollowEntity(int idx, int userId, int following) {
        this.idx = idx;
        this.userId = userId;
        this.following = following;
    }
}
