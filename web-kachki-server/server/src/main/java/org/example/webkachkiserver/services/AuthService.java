package org.example.webkachkiserver.services;

import org.example.webkachkiserver.components.HashComponent;
import org.example.webkachkiserver.models.user.User;
import org.example.webkachkiserver.repositrories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private HashComponent hashComponent;
    @Autowired
    private JWTService jwtService;

    public ResponseEntity<?> register(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            return ResponseEntity.badRequest().body("Email address already in use");
        }
        user.setPassword(hashComponent.hashToString(user.getPassword()));
        userRepository.save(user);
        return ResponseEntity.ok(jwtService.generateToken(user.getEmail()));
    }

    public ResponseEntity<?> login(User user) {
        User dbUser = userRepository.findByEmail(user.getEmail());
        if(dbUser != null && hashComponent.hashToString(user.getPassword()).equals(dbUser.getPassword())) {
            return ResponseEntity.ok().body(jwtService.generateToken(user.getEmail()));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }
    }

    public ResponseEntity<?> getId(String token) {
        String email = jwtService.getDataFromToken(token);
        long id = userRepository.findByEmail(email).getId();
        return ResponseEntity.ok(id);
    }
}
