package com.travelmaker.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Hashtag {
    /** 태그 번호 */
    private int tagId;
    /** 태그 이름 */
    private String tagName;

    @Builder
    public Hashtag(int tagId, String tagName) {
        this.tagId = tagId;
        this.tagName = tagName;
    }
}
