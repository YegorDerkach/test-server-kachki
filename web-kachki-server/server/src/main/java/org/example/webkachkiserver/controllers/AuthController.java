package org.example.webkachkiserver.controllers;

import org.example.webkachkiserver.models.user.User;
import org.example.webkachkiserver.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        return authService.register(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login (@RequestBody User user) {
        return authService.login(user);
    }
    @GetMapping("/id")
    public ResponseEntity<?> getId (@RequestParam String token) {
        return authService.getId(token);
    }
}
