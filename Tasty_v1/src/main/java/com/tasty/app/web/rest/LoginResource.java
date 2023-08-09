package com.tasty.app.web.rest;

import com.tasty.app.repository.AdminRepository;
import com.tasty.app.request.InfoRequest;
import com.tasty.app.request.RegistryRequest;
import com.tasty.app.request.VerifyRequest;
import com.tasty.app.service.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/login")
public class LoginResource {
    @Autowired
    private LoginService loginService;

    @PostMapping("/registry")
    public ResponseEntity registry(@RequestBody RegistryRequest request) {
        String response = loginService.registry(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/registry/verify")
    public ResponseEntity verify(@RequestBody VerifyRequest request) {
        String response = loginService.verify(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/registry/resend-code")
    public ResponseEntity resendCode(@RequestParam String email) {
        String response = loginService.resendCode(email);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/fill-info")
    public ResponseEntity fillInfo(@RequestBody InfoRequest request) {
        String response = loginService.fillInfo(request);
        return ResponseEntity.ok(response);
    }
}
