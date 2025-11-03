package org.example.webkachkiserver.configs;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

@Configuration
public class GcsConfig {
    @Value("${gcp.credentials.location:}")
    private String credentialsPath;

    @Bean
    public Storage storage() throws IOException {
        // First, try to use GOOGLE_APPLICATION_CREDENTIALS environment variable (standard GCP way)
        String googleAppCredentials = System.getenv("GOOGLE_APPLICATION_CREDENTIALS");
        
        if (googleAppCredentials != null && !googleAppCredentials.isEmpty()) {
            // Use standard GCP credentials from environment variable
            return StorageOptions.getDefaultInstance().getService();
        }
        
        // Fallback to credentials path from application.properties
        if (credentialsPath != null && !credentialsPath.isEmpty()) {
            GoogleCredentials credentials = GoogleCredentials.fromStream(
                            new FileInputStream(credentialsPath))
                    .createScoped(Arrays.asList("https://www.googleapis.com/auth/cloud-platform"));

            return StorageOptions.newBuilder()
                    .setCredentials(credentials)
                    .build()
                    .getService();
        }
        
        // If neither is set, try default credentials (useful for GCP instances)
        try {
            return StorageOptions.getDefaultInstance().getService();
        } catch (Exception e) {
            throw new IllegalStateException(
                "GCP credentials not found. Set GOOGLE_APPLICATION_CREDENTIALS environment variable or gcp.credentials.location in application.properties", 
                e
            );
        }
    }
}
