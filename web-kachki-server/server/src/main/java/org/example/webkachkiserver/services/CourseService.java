package org.example.webkachkiserver.services;

import org.example.webkachkiserver.models.course.Course;
import org.example.webkachkiserver.models.user.User;
import org.example.webkachkiserver.repositrories.CourseRepository;
import org.example.webkachkiserver.repositrories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<?> addCourse(Course course, long courseId) {
        if (courseRepository.existsById(courseId)) {
            return ResponseEntity.badRequest().body("Course already exists");
        }
        course.setCourseId(courseId);
        courseRepository.save(course);
        return ResponseEntity.ok().build();
    }
    public ResponseEntity<?> removeCourse(long courseId) {
        courseRepository.deleteById(courseId);
        return ResponseEntity.ok().build();
    }
    public ResponseEntity<?> buyCourse(long userId, long courseId) {
        User mongoUser = userRepository.findById(userId);
        mongoUser.getBoughtCoursesId().add(courseId);
        return ResponseEntity.ok().body("Course bought!");
    }
    public ResponseEntity<?> checkCourse(long userId, long courseId) {
        User mongoUser = userRepository.findById(userId);
        if (!mongoUser.getBoughtCoursesId().contains(courseId)) {
            return ResponseEntity.badRequest().body("Course not avaliable");
        } else {
            return ResponseEntity.ok().build();
        }
    }
}
