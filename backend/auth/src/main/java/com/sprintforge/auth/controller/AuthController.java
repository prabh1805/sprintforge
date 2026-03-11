package com.sprintforge.auth.controller;

import com.sprintforge.auth.dto.LoginRequest;
import com.sprintforge.auth.dto.LoginResponse;
import com.sprintforge.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        LoginResponse result = authService.login(loginRequest.getEmail(),  loginRequest.getPassword());
        return ResponseEntity.ok(result);
    }
}
