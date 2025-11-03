package org.example.webkachkiserver.services;

import org.example.webkachkiserver.models.lesson.Lesson;
import org.example.webkachkiserver.repositrories.LessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class LessonService {
    @Autowired
    private LessonRepository lessonRepository;

    public ResponseEntity<?> createLesson(Lesson lesson, long lessonId, long courseId) {
        lesson.setId(lessonId);
        lesson.setCourseId(courseId);
        lessonRepository.save(lesson);
        return ResponseEntity.ok(lesson);
    }

    public ResponseEntity<?> removeLesson(long lessonId) {
        lessonRepository.deleteById(lessonId);
        return ResponseEntity.ok().body("Removed");
    }
}
