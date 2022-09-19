package com.travelmaker.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @Column(name="profile_img")
    private String profile_img;
    @Column(name="role")
    private String role;

    @Builder
    public UserEntity(int id, String email, String user_id, String password, String profile_img, String role) {
        this.id = id;
        this.user_id = user_id;
        this.email = email;
        this.password = password;
        this.profile_img = profile_img;
        this.role = role;
    }


}
