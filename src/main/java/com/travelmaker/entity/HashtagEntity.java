package com.travelmaker.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "hashtags")
public class HashtagEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idx")
    private int idx;

    @Column(name="tag_name")
    private String tag_name;

    @Builder
    public HashtagEntity(int idx, String tag_name) {
        this.idx = idx;
        this.tag_name = tag_name;
    }
}
