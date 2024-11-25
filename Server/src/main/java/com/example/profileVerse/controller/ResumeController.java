package com.example.profileVerse.controller;

import com.example.profileVerse.entity.Resume;
import com.example.profileVerse.service.ResumeService;
import com.example.profileVerse.utils.PDFUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
            // Extract batchId and files from the payload
            Long batchId = Long.valueOf(payload.get("batchId").toString());
            List<String> fileBase64List = (List<String>) payload.get("files");

            // Convert base64 strings to MultipartFile equivalents (if needed)
            List<MultipartFile> files = PDFUtils.convertBase64ToMultipartFile(fileBase64List);

            // Call the service layer
            resumeService.uploadResumes(batchId, files);

            return ResponseEntity.ok("Resumes uploaded successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
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
