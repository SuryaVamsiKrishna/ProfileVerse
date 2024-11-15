package com.example.profileVerse.service;

import com.example.profileVerse.entity.Batch;
import com.example.profileVerse.entity.Resume;
import com.example.profileVerse.repository.BatchRepository;
import com.example.profileVerse.repository.ResumeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return resumeRepository.findByBatch_BatchId(batchId);
    }

    /**
     * Upload a single resume using byte array
     */
    @Override
    public Resume uploadResume(Long batchId, byte[] fileBytes) {
        Batch batch = batchRepository.findById(batchId)
                .orElseThrow(() -> new RuntimeException("Batch not found with ID: " + batchId));
        Resume resume = new Resume(batch, fileBytes);
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

                // Create a new Resume object and save it
                Resume resume = new Resume(batch, fileBytes);
                resumeRepository.save(resume);

            } catch (IllegalArgumentException e) {
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
