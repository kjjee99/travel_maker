package com.travelmaker.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class User {
    private String id;
    private String email;
    private String password;
    private String profile_img;
//    private String post_id;
    private String role;

    @Builder
    public User(String id, String email, String password, String profile_img, String role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.profile_img = profile_img;
//        this.post_id = post_id;
        this.role = role;
    }
}
