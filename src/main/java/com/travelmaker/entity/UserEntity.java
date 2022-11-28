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
    private String user_id;
    @Column(name="password")
    private String password;
    @Column(name = "phonenumber")
    private String phoneNumber;
    @Column(name="profileimg")
    private String profileImg;
    @Column(name="role")
    private String role;

    public UserEntity(int idx, String user_id, String profileImg){
        this.idx = idx;
        this.user_id = user_id;
        this.profileImg = profileImg;
    }

    @Builder
    public UserEntity(int idx, String email, String user_id, String password, String phoneNumber, String profileImg, String role) {
        this.idx = idx;
        this.user_id = user_id;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.profileImg = profileImg;
        this.role = role;
    }


}
