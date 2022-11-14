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
    @Column(name="id")
    private int id;
    @Column(name="email")
    private String email;
    @Column(name="user_id")
    private String user_id;
    @Column(name="password")
    private String password;
    @Column(name = "phone_number")
    private String phone_number;
    @Column(name="profile_img")
    private String profile_img;
    @Column(name="role")
    private String role;

    public UserEntity(int id, String user_id, String profile_img){
        this.id = id;
        this.user_id = user_id;
        this.profile_img = profile_img;
    }

    @Builder
    public UserEntity(int id, String email, String user_id, String password, String phone_number, String profile_img, String role) {
        this.id = id;
        this.user_id = user_id;
        this.email = email;
        this.password = password;
        this.phone_number = phone_number;
        this.profile_img = profile_img;
        this.role = role;
    }


}
