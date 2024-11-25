package com.example.profileVerse.service;

import com.example.profileVerse.entity.Resume;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ResumeService {
    List<Resume> getAllResumes();

    List<Resume> getResumesByBatchId(Long batchId);

    // Update to use MultipartFile for file upload
    void uploadResumes(Long batchId, List<MultipartFile> files);

    void deleteResume(Long resumeId);
}
