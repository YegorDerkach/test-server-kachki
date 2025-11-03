package org.example.webkachkiserver.controllers;

import org.example.webkachkiserver.models.lesson.Lesson;
import org.example.webkachkiserver.repositrories.LessonRepository;
import org.example.webkachkiserver.services.VideoStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/courses")
public class VideoController {

    @Autowired
    private VideoStorageService videoStorageService;

    @Autowired
    private LessonRepository lessonRepository;

    @PostMapping("/{courseId}/{lessonId}/upload")
    public ResponseEntity<?> uploadVideo(@PathVariable long courseId,
                                         @PathVariable long lessonId,
                                         @RequestParam("file") MultipartFile file) {
        try {
            String gcsPath = videoStorageService.uploadVideo(file, courseId);
            Lesson lesson = lessonRepository.findById(lessonId);
            lesson.setVideoFileName(gcsPath);
            lessonRepository.save(lesson);
            return ResponseEntity.ok(lesson);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Upload failed: " + e.getMessage());
        }
    }

    @GetMapping("/{courseId}/lessons")
    public List<Lesson> getLessons(@PathVariable long courseId) {
        return videoStorageService.getLessons(courseId);
    }
    @GetMapping("/{courseId}/{lessonId}/video")
    public ResponseEntity<?> getUrl(@PathVariable long lessonId) {
        return videoStorageService.getUrl(lessonId);
    }
}
