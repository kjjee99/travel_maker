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
    private String phoneNumber;
    private String profileImg;
    private String role;

    @Builder
    public User(String id, String email, String password, String phoneNumber, String profileImg, String role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.profileImg = profileImg;
        this.role = role;
    }
}
