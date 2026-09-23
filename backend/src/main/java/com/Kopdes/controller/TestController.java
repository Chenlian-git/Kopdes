package com.Kopdes.controller;

import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class TestController {

    private final Firestore firestore;

    public TestController(Firestore firestore) {
        this.firestore = firestore;
    }

    @GetMapping("/api/test-firestore")
    public String testFirestore() throws Exception {

        Map<String, Object> data = new HashMap<>();
        data.put("message", "Firestore berhasil terhubung");
        data.put("test", true);

        // Tulis data
        firestore.collection("test")
                .document("connection")
                .set(data)
                .get();

        // Baca kembali data
        DocumentSnapshot document = firestore.collection("test")
                .document("connection")
                .get()
                .get();

        if (document.exists()) {
            return "Koneksi Firestore BERHASIL! Data berhasil ditulis dan dibaca.";
        }

        return "Firestore terhubung, tetapi data tidak ditemukan.";
    }
}