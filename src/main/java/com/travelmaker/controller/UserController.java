package com.travelmaker.controller;

import com.travelmaker.dto.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface UserController {
    /* 회원가입 */
    ResponseEntity addUser(@RequestBody User user);
}
