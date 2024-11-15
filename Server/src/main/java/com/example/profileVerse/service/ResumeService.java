package com.example.profileVerse.service;

import com.example.profileVerse.entity.Resume;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ResumeService {
    List<Resume> getAllResumes();

    List<Resume> getResumesByBatchId(Long batchId);

    // Upload a single resume with byte array
    Resume uploadResume(Long batchId, byte[] fileBytes);

    // Upload multiple resumes as base64 strings
    void uploadResumes(Long batchId, List<String> base64Files);

    void deleteResume(Long resumeId);
}
