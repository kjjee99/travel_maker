package com.travelmaker.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    private String id;
    private String email;
    private String password;
    private String phoneNumber;
    private String profileImg;
    private int following;
    private int follower;
    private String role;
}
