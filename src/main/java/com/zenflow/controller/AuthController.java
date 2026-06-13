package com.zenflow.controller;

import com.zenflow.data.entity.User;
import com.zenflow.service.AuthService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest request) {
        try {
            User user = authService.register(request.getEmail(), request.getPassword(), request.getDisplayName());
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        return authService.login(request.getEmail(), request.getPassword())
                .map(user -> ResponseEntity.ok(new AuthResponse(jwtService.generateToken(user.getEmail()))))
                .orElse(ResponseEntity.status(401).body("Invalid credentials"));
    }

    @Data
    public static class SignupRequest {
        private String email;
        private String password;
        private String displayName;
    }

    @Data
    public static class LoginRequest {
        private String email;
        private String password;
    }

    @Data
    @RequiredArgsConstructor
    public static class AuthResponse {
        private final String token;
    }
}
