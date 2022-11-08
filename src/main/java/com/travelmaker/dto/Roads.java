package com.travelmaker.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Roads {

    private String placeName;
    private String tips;

    @Builder
    public Roads(String placeName, String tips) {
        this.placeName = placeName;
        this.tips = tips;
    }
}
