package com.tasty.app.web.rest;

import com.tasty.app.request.InfoRequest;
import com.tasty.app.request.RegistryRequest;
import com.tasty.app.request.VerifyRequest;
import com.tasty.app.response.InfoResponse;
import com.tasty.app.response.RegistryResponse;
import com.tasty.app.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class LoginResource {
    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity login() {
        Map<String, String> response = new HashMap<>();
        response.put("jwtToken", "123456789");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/registry")
    public ResponseEntity registry(@RequestBody RegistryRequest request) {
        RegistryResponse response = loginService.registry(request);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PostMapping("/registry/verify")
    public ResponseEntity verify(@RequestBody VerifyRequest request) {
        String response = loginService.verify(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/registry/resend-code")
    public ResponseEntity resendCode(@RequestParam String email) {
        String response = loginService.sendCode(email);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/customer/fill-info")
    public ResponseEntity fillInfo(@RequestBody InfoRequest request) {
        InfoResponse response = loginService.fillInfo(request);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
