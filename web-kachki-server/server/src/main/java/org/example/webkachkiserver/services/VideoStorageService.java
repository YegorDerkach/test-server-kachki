package org.example.webkachkiserver.services;

import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import lombok.extern.slf4j.Slf4j;
import org.example.webkachkiserver.models.lesson.Lesson;
import org.example.webkachkiserver.repositrories.LessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class VideoStorageService {

    @Autowired
    private Storage storage;
    @Autowired
    private LessonRepository lessonRepository;

    @Value("${gcs.bucket.video.name}")
    private String bucketName;

    public String uploadVideo(MultipartFile file, long courseId) throws IOException {
        log.info("Storage credentials: {}", storage.getOptions().getCredentials());
        String blobName = "videos/course" + courseId + "/" + file.getOriginalFilename();
        BlobInfo blobInfo = BlobInfo.newBuilder(bucketName, blobName)
                .setContentType(file.getContentType())
                .build();
        storage.create(blobInfo, file.getBytes());
        return blobName;
    }

    public String getSignedUrl(String objectName) {
        BlobInfo blobInfo = BlobInfo.newBuilder(bucketName, objectName).build();
        URL signedUrl = storage.signUrl(
                blobInfo,
                15, TimeUnit.MINUTES,
                Storage.SignUrlOption.withV4Signature()
        );
        return signedUrl.toString();
    }
    public List<Lesson> getLessons(long courseId) {
        List<Lesson> lessons = lessonRepository.findByCourseId(courseId);
        return lessons.stream().map(lesson -> {
            String signedUrl = getSignedUrl(lesson.getVideoFileName());
            return Lesson.builder()
                    .courseId(lesson.getCourseId())
                    .id(lesson.getId())
                    .description(lesson.getDescription())
                    .title(lesson.getTitle())
                    .teacherId(lesson.getTeacherId())
                    .videoFileName(signedUrl)
                    .build();
        }).toList();
    }
    public ResponseEntity<?> getUrl(long lessonId) {
        return ResponseEntity.ok(lessonRepository.findById(lessonId).getVideoFileName());
    }
}
