package org.example.webkachkiserver.controllers;

import org.example.webkachkiserver.models.lesson.Lesson;
import org.example.webkachkiserver.services.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/lessons")
public class LessonController {
    @Autowired
    private LessonService lessonService;

    /*Виникає питання, що таке courseId у цьому випадку
    /*Як я це бачу: юзер відкриває сторінку з курсами
    /*Відповідно тепер в нього вона є типу
    /*http://бімбімбамбам/courseId
    /*І там тепер є кнопочка типу (додати урок)
    /*Коли він по ній переходить, генерується lessonId
    /*(аналогічно до courseId) і його перекидує на нову сторінку
    /*http://бімбімбамбам/courseId/lessonId
    /*Відповідно таким крутим чином можна на ізі визначити
    /*ДО ЯКОГО КУРСУ СТВОРЮЄТЬСЯ УРОК*/

    @PostMapping("/{courseId}/{lessonId}/create")
    public ResponseEntity<?> createLesson(@RequestBody Lesson lesson,
                                               @PathVariable long courseId,
                                               @PathVariable long lessonId) {
        return lessonService.createLesson(lesson, courseId, lessonId);
    }
    @PostMapping("/{courseId}/{lessonId}/remove")
    public ResponseEntity<?> removeLesson(@PathVariable long lessonId) {
        return lessonService.removeLesson(lessonId);
    }
}
