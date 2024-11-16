package com.example.profileVerse.controller;

import com.example.profileVerse.entity.Resume;
import com.example.profileVerse.service.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Base64;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/resumes")
@CrossOrigin(origins = "http://localhost:3000")
public class ResumeController {

    @Autowired
    private ResumeService resumeService;

    /**
     * Fetch all resumes
     */
    @GetMapping
    public ResponseEntity<List<Resume>> getAllResumes() {
        List<Resume> resumes = resumeService.getAllResumes();
        return ResponseEntity.ok(resumes);
    }

    /**
     * Fetch resumes by batch ID
     */
    @GetMapping("/batch/{batchId}")
    public ResponseEntity<List<Resume>> getResumesByBatchId(@PathVariable Long batchId) {
        List<Resume> resumes = resumeService.getResumesByBatchId(batchId);
        return ResponseEntity.ok(resumes);
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadResumes(@RequestBody Map<String, Object> payload) {
        try {
            Long batchId = Long.valueOf(payload.get("batchId").toString());
            List<String> files = (List<String>) payload.get("files");

            // Upload each resume
            resumeService.uploadResumes(batchId, files);

            return ResponseEntity.ok("Resumes uploaded successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error uploading resumes: " + e.getMessage());
        }
    }

    /**
     * Delete a specific resume by ID
     */
    @DeleteMapping("/{resumeId}")
    public ResponseEntity<Void> deleteResume(@PathVariable Long resumeId) {
        resumeService.deleteResume(resumeId);
        return ResponseEntity.noContent().build();
    }
}
