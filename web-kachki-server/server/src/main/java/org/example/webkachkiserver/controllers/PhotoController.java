package org.example.webkachkiserver.controllers;

import org.example.webkachkiserver.models.user.User;
import org.example.webkachkiserver.repositrories.UserRepository;
import org.example.webkachkiserver.services.PhotoStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("api/photo")
public class PhotoController {

    @Autowired
    private PhotoStorageService photoStorageService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/{userId}/upload")
    public ResponseEntity<?> uploadVideo(@PathVariable long userId,
                                         @RequestParam("file") MultipartFile file) {
        try {
            String gcsPath = photoStorageService.uploadUserProfilePhoto(file, userId);
            User user = userRepository.findById(userId);
            user.setPathToPhoto(gcsPath);
            if (!userRepository.existsById(userId)) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            userRepository.save(user);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Upload failed: " + e.getMessage());
        }
    }

    @GetMapping("/{userId}/user")
    public User getUser(@PathVariable long userId) {
        return photoStorageService.getUser(userId);
    }
    @GetMapping("/{userId}/photo")
    public ResponseEntity<?> getUrl(@PathVariable long userId) {
        return photoStorageService.getUrl(userId);
    }
}
