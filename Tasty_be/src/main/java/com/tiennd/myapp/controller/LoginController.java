package com.tiennd.myapp.controller;

import com.tiennd.myapp.constain.LoginForm;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class LoginController {

    @PostMapping(value = "/login", consumes="application/json")
    public ResponseEntity<String> login(@RequestBody LoginForm loginForm) {
        return ResponseEntity.ok("Success");
    }
}
