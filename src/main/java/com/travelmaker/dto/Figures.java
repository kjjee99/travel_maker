package com.travelmaker.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Figures {

    private int recommend;
    private int emotion;
    private int revisit;

    @Builder
    public Figures(int recommend, int emotion, int revisit) {
        this.recommend = recommend;
        this.emotion = emotion;
        this.revisit = revisit;
    }

}
