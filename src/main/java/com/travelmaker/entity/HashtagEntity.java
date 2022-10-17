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
    @Column(name = "id")
    private int id;

    @Column(name="tag_name")
    private String tag_name;

    @Builder
    public HashtagEntity(int id, String tag_name) {
        this.id = id;
        this.tag_name = tag_name;
    }
}
