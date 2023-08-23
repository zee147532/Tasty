package com.tasty.app.web.rest;

import com.tasty.app.request.InfoRequest;
import com.tasty.app.request.RegistryRequest;
import com.tasty.app.request.VerifyRequest;
import com.tasty.app.response.HttpResponse;
import com.tasty.app.response.InfoResponse;
import com.tasty.app.response.RegistryResponse;
import com.tasty.app.service.LoginService;
import com.tasty.app.service.dto.CustomerDetail;
import com.tasty.app.web.rest.errors.BadRequestAlertException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class LoginResource {
    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody CustomerDetail customerDetail) {
        try {
            Map<String, String> response = loginService.customerLogin(customerDetail);
            return ResponseEntity.ok(response);
        } catch (BadRequestAlertException e) {
            return ResponseEntity.status(400).body(Map.of("errorMsg", e.getMessage()));
        }
    }

    @PostMapping("/registry")
    public ResponseEntity registry(@RequestBody RegistryRequest request) {
        RegistryResponse response = loginService.registry(request);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PostMapping("/registry/verify")
    public ResponseEntity verify(@RequestBody VerifyRequest request) {
        HttpResponse response = loginService.verify(request);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PostMapping("/registry/resend-code")
    public ResponseEntity resendCode(@RequestParam String email) {
        try {
            String response = loginService.sendCode(email);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Không thể gửi mã xác thực đến email của bạn, vui lòng thử lại sau ít phút.");
        }
    }

    @PostMapping("/customer/fill-info")
    public ResponseEntity fillInfo(@RequestBody InfoRequest request) {
        InfoResponse response = loginService.fillInfo(request);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
