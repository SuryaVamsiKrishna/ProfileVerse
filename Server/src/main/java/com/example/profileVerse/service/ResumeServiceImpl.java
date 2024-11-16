package com.example.profileVerse.service;

import com.example.profileVerse.entity.Batch;
import com.example.profileVerse.entity.Resume;
import com.example.profileVerse.repository.BatchRepository;
import com.example.profileVerse.repository.ResumeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;

@Service
public class ResumeServiceImpl implements ResumeService {

    @Autowired
    private ResumeRepository resumeRepository;

    @Autowired
    private BatchRepository batchRepository;

    /**
     * Fetch all resumes
     */
    @Override
    public List<Resume> getAllResumes() {
        return resumeRepository.findAll();
    }

    /**
     * Fetch resumes by batch ID
     */
    @Override
    public List<Resume> getResumesByBatchId(Long batchId) {
        return resumeRepository.findByBatchId(batchId);
    }

    /**
     * Upload a single resume using byte array
     */
    @Override
    public Resume uploadResume(Long batchId, byte[] fileBytes) {
        // Fetch the batch from the database
        Batch batch = batchRepository.findById(batchId)
                .orElseThrow(() -> new RuntimeException("Batch not found with ID: " + batchId));

        // Create a new Resume object with the current timestamp
        Resume resume = new Resume();
        resume.setBatch(batch);
        resume.setResumeFile(fileBytes);
        resume.setCreatedAt(LocalDateTime.now());
        resume.setUpdatedAt(LocalDateTime.now());

        // Save and return the Resume
        return resumeRepository.save(resume);
    }

    /**
     * Upload multiple resumes using a list of base64 encoded files
     */
    @Override
    public void uploadResumes(Long batchId, List<String> base64Files) {
        Batch batch = batchRepository.findById(batchId)
                .orElseThrow(() -> new RuntimeException("Batch not found with ID: " + batchId));

        base64Files.forEach(fileBase64 -> {
            try {
                // Decode the base64 string to byte array
                byte[] fileBytes = Base64.getDecoder().decode(fileBase64);

                // Create a new Resume object
                Resume resume = new Resume();
                resume.setBatch(batch);
                resume.setCreatedAt(LocalDateTime.now());
                resume.setResumeFile(fileBytes);
                resume.setUpdatedAt(LocalDateTime.now());
                System.out.println("resume: " + resume.getResumeFile());
                // Debug print statements
                System.out.println("Resume File Type: " + (fileBytes instanceof byte[] ? "byte[]" : "Not byte[]"));
                System.out.println("Batch ID: " + (batch != null ? batch.getBatchId() : "null"));

                // Save the resume

                resumeRepository.save(resume);
                System.out.println("Resume saved successfully");

            } catch (IllegalArgumentException e) {
                System.err.println("Error decoding base64 file: " + e.getMessage());
                throw new RuntimeException("Invalid base64 file data provided", e);
            }
        });
    }

    /**
     * Delete a specific resume by its ID
     */
    @Override
    public void deleteResume(Long resumeId) {
        if (resumeRepository.existsById(resumeId)) {
            resumeRepository.deleteById(resumeId);
        } else {
            throw new RuntimeException("Resume not found with ID: " + resumeId);
        }
    }
}
