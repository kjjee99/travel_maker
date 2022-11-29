package com.travelmaker.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Follow {

    private int id;
    private int userIdx;
    private int following;

    @Builder
    public Follow(int id, int userIdx, int following) {
        this.id = id;
        this.userIdx = userIdx;
        this.following = following;
    }
}
