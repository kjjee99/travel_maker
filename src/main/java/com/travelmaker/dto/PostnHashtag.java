package com.travelmaker.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class PostnHashtag implements Serializable {
    private int postId;
    private int tagId;
}
