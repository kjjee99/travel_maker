package com.travelmaker.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="user")
public class UserEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idx;
    private String email;
    @Column(name="userid", nullable = false)
    private String userId;
    private String password;
    @Column(name = "phonenumber", nullable = false)
    private String phoneNumber;
    @Column(name="profileimg", nullable = false)
    private String profileImg;
    private String role;

    public UserEntity(int idx, String userid, String profileimg){
        this.idx = idx;
        this.userId = userid;
        this.profileImg = profileimg;
    }

    @Builder
    public UserEntity(int idx, String email, String userId, String password, String phoneNumber, String profileImg, String role) {
        this.idx = idx;
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.profileImg = profileImg;
        this.role = "USER";
    }


}
