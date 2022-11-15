package com.travelmaker.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Follow {

    private int id;
    private int userId;
    private int following;

    @Builder
    public Follow(int id, int userId, int following) {
        this.id = id;
        this.userId = userId;
        this.following = following;
    }
}
