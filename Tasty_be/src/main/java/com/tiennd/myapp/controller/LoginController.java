package com.tiennd.myapp.controller;

import com.tiennd.myapp.constain.LoginForm;
import com.tiennd.myapp.response.LoginResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class LoginController {

    @GetMapping(value = "/login", consumes="application/json")
    public ResponseEntity login(@RequestBody LoginForm loginForm) {
        return ResponseEntity.ok(new LoginResponse("abcdefghijklmnopqrstuvwxyz"));
    }
}
