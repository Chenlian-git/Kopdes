package com.Kopdes.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.google.cloud.firestore.Firestore;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Configuration
public class FirebaseConfig {

    public FirebaseConfig() throws IOException {

        if (FirebaseApp.getApps().isEmpty()) {

            String firebaseCredentials =
                    System.getenv("FIREBASE_SERVICE_ACCOUNT");

            GoogleCredentials credentials;

            if (firebaseCredentials != null && !firebaseCredentials.isBlank()) {

                // Production / Deployment
                credentials = GoogleCredentials.fromStream(
                        new ByteArrayInputStream(
                                firebaseCredentials.getBytes(StandardCharsets.UTF_8)
                        )
                );

            } else {

                // Local development
                FileInputStream serviceAccount =
                        new FileInputStream("firebase-service-account.json");

                credentials = GoogleCredentials.fromStream(serviceAccount);
            }

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(credentials)
                    .build();

            FirebaseApp.initializeApp(options);
        }
    }

    @Bean
    public Firestore firestore() {
        return FirestoreClient.getFirestore();
    }
}