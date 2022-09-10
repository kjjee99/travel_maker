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
    private int user_id;
    @Column(name="email")
    private String email;
    @Column(name="user_id")
    private String id;
    @Column(name="password")
    private String password;
    @Column(name="profile_img")
    private String profile_img;
    // json
    @Column(name="post_id")
    private String post_id;
    @Column(name="role")
    private String role;

    @Builder
    public UserEntity(int user_id, String email, String id, String password, String profile_img, String post_id, String role) {
        this.user_id = user_id;
        this.email = email;
        this.id = id;
        this.password = password;
        this.profile_img = profile_img;
        this.post_id = post_id;
        this.role = role;
    }


}
