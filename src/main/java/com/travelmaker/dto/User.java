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
    private String phone_number;
    private String profile_img;
    private String role;

    @Builder
    public User(String id, String email, String password, String phone_number, String profile_img, String role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.phone_number = phone_number;
        this.profile_img = profile_img;
        this.role = role;
    }
}
