package org.example.webkachkiserver.services;

import org.example.webkachkiserver.models.course.Course;
import org.example.webkachkiserver.models.user.User;
import org.example.webkachkiserver.repositrories.CourseRepository;
import org.example.webkachkiserver.repositrories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    private CourseRepository courseRepository;

    public ResponseEntity<?> getUser(long userId) {
        return ResponseEntity.ok(userRepository.findById(userId));
    }
    public ResponseEntity<?> changeDescription(long userId, String description) {
        User mongoUser = userRepository.findById(userId);
        mongoUser.setDescription(description);
        userRepository.save(mongoUser);
        return ResponseEntity.ok(mongoUser.getDescription());
    }
    public ResponseEntity<?> changeName(long userId, String name) {
        User mongoUser = userRepository.findById(userId);
        mongoUser.setName(name);
        userRepository.save(mongoUser);
        return ResponseEntity.ok(mongoUser.getName());
    }
    public ResponseEntity<?> getCourses(long userId) {
        List<Course> courses = courseRepository.findByUserId(userId);
        return ResponseEntity.ok(courses);
    }
    public ResponseEntity<?> changeInstagram(long userId, String instagramUrl) {
        User mongoUser = userRepository.findById(userId);
        mongoUser.getContacts().setInstUrl(instagramUrl);
        userRepository.save(mongoUser);
        return ResponseEntity.ok(mongoUser.getContacts().getInstUrl());
    }
    public ResponseEntity<?> changeFacebook(long userId, String facebookUrl) {
        User mongoUser = userRepository.findById(userId);
        mongoUser.getContacts().setFacebookUrl(facebookUrl);
        userRepository.save(mongoUser);
        return ResponseEntity.ok(mongoUser.getContacts().getFacebookUrl());
    }
    public ResponseEntity<?> changeLinkedIn(long userId, String linkedInUrl) {
        User mongoUser = userRepository.findById(userId);
        mongoUser.getContacts().setLinkedInUrl(linkedInUrl);
        userRepository.save(mongoUser);
        return ResponseEntity.ok(mongoUser.getContacts().getLinkedInUrl());
    }
    public ResponseEntity<?> changeTelegram(long userId, String telegramUrl) {
        User mongoUser = userRepository.findById(userId);
        mongoUser.getContacts().setTelegramUrl(telegramUrl);
        userRepository.save(mongoUser);
        return ResponseEntity.ok(mongoUser.getContacts().getTelegramUrl());
    }
}
