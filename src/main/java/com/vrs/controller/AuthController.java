package com.vrs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import com.vrs.enums.Role;
import com.vrs.model.User;
import com.vrs.security.JwtUtil;
import com.vrs.service.UserService;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.registerUser(user));
    }

    @PostMapping("/register-admin")
    public ResponseEntity<User> registerAdmin(@RequestBody User user) {
        return ResponseEntity.ok(userService.registerAdmin(user));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {

        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    request.getEmail(),
                    request.getPassword()
                )
            );
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(401).build();
        }

        User user = userService.getByEmail(request.getEmail());

        String token = jwtUtil.generateToken(
                user.getEmail(),
                user.getRole().name()
        );

        return ResponseEntity.ok(new LoginResponse(token, user.getRole()));
    }

    // DTOs
    static class LoginRequest {
        private String email;
        private String password;
        public String getEmail() { return email; }
        public String getPassword() { return password; }
        public void setEmail(String email) { this.email = email; }
        public void setPassword(String password) { this.password = password; }
    }

    static class LoginResponse {
        private String token;
        private Role role;

        public LoginResponse(String token, Role role) {
            this.token = token;
            this.role = role;
        }

        public String getToken() { return token; }
        public Role getRole() { return role; }
    }
}
