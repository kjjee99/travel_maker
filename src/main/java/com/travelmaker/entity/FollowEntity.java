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
    @Column(name = "id")
    private int id;
    @Column(name = "user_id")
    private int userId;
    @Column(name = "following")
    private int following;

    @Builder
    public FollowEntity(int id, int userId, int following) {
        this.id = id;
        this.userId = userId;
        this.following = following;
    }
}
