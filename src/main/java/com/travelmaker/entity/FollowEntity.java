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
    @Column(name = "useridx")
    private int userIdx;
    @Column(name = "following")
    private int following;

    @Builder
    public FollowEntity(int idx, int userIdx, int following) {
        this.idx = idx;
        this.userIdx = userIdx;
        this.following = following;
    }
}
