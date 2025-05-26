package com.perfulandiaspa.autentificacion.controller;

import com.perfulandiaspa.autentificacion.model.User;
import com.perfulandiaspa.autentificacion.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        return ResponseEntity.ok(authService.registerUser(user));
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestParam String username, 
                                    @RequestParam String password) {
        return ResponseEntity.ok(authService.authenticate(username, password));
    }

    @GetMapping("/users/{username}")
    public ResponseEntity<User> getUser(@PathVariable String username) {
        return ResponseEntity.ok(authService.authenticate(username, "dummy"));
    }
}