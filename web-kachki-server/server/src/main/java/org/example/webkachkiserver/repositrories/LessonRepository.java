package org.example.webkachkiserver.repositrories;

import org.example.webkachkiserver.models.lesson.Lesson;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonRepository extends MongoRepository<Lesson, Long> {
    List<Lesson> findByCourseId(long courseId);
    Lesson findById(long lessonId);
    boolean existsById(long lessonId);
}
