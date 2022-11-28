package com.travelmaker.entity;

import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@TypeDef(name = "json", typeClass = JsonStringType.class)
@Table(name="user")
public class UserEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idx")
    private int idx;
    @Column(name="email")
    private String email;
    @Column(name="userid")
    private String userId;
    @Column(name="password")
    private String password;
    @Column(name = "phonenumber")
    private String phoneNumber;
    @Column(name="profileimg")
    private String profileImg;
    @Column(name="role")
    private String role;

    public UserEntity(int idx, String userId, String profileImg){
        this.idx = idx;
        this.userId = userId;
        this.profileImg = profileImg;
    }

    @Builder
    public UserEntity(int idx, String email, String userId, String password, String phoneNumber, String profileImg, String role) {
        this.idx = idx;
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.profileImg = profileImg;
        this.role = role;
    }


}
