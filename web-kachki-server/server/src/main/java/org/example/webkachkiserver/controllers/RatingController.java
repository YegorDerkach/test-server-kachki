package org.example.webkachkiserver.controllers;

import org.example.webkachkiserver.models.course.Course;
import org.example.webkachkiserver.repositrories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/rating")
public class RatingController {
    @Autowired
    private CourseRepository courseRepository;
    @PostMapping("/{courseId}/rating")
    public ResponseEntity<?> updateRating(@RequestParam int rate, @PathVariable long courseId) {
        Course course = courseRepository.findByCourseId(courseId);
        course.setRates(course.getRates() + 1);
        course.setRating(Math.round((
                (course.getRating() + rate) / course.getRates()) * 10) / 10);
        courseRepository.save(course);
        return ResponseEntity.ok().build();
    }
}
