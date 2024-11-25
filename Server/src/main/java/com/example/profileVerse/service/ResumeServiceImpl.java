package com.example.profileVerse.service;

import com.example.profileVerse.entity.Batch;
import com.example.profileVerse.entity.Resume;
import com.example.profileVerse.repository.BatchRepository;
import com.example.profileVerse.repository.ResumeRepository;
import com.example.profileVerse.utils.PDFUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ResumeServiceImpl implements ResumeService {

    @Autowired
    private ResumeRepository resumeRepository;

    @Autowired
    private BatchRepository batchRepository;

    @Override
    public List<Resume> getAllResumes() {
        return resumeRepository.findAll();
    }

    @Override
    public List<Resume> getResumesByBatchId(Long batchId) {
        return resumeRepository.findByBatchId(batchId);
    }

    @Override
    public void uploadResumes(Long batchId, List<MultipartFile> files) {

        files.forEach(file -> {
            try {
                // Save the file temporarily
                File tempFile = convertMultipartFileToFile(file);

                // Extract text from PDF
                String extractedText = PDFUtils.extractTextFromPDF(tempFile);
                String githubLink = PDFUtils.extractGitHubLink(tempFile);

                System.out.print(extractedText);

                // Create a new Resume object
                Resume resume = new Resume();
                resume.setBatchId(batchId);
                resume.setResumeText(extractedText);
                resume.setResumeGitHubLink(githubLink);
                resume.setCreatedAt(LocalDateTime.now());
                resume.setUpdatedAt(LocalDateTime.now());

                // Save the resume to the database
                resumeRepository.save(resume);

                System.out.print("Success");

                // Clean up the temporary file
                tempFile.delete();
            } catch (Exception e) {
                throw new RuntimeException("Error processing resume file", e);
            }
        });
    }

    @Override
    public void deleteResume(Long resumeId) {
        if (resumeRepository.existsById(resumeId)) {
            resumeRepository.deleteById(resumeId);
        } else {
            throw new RuntimeException("Resume not found with ID: " + resumeId);
        }
    }

    private File convertMultipartFileToFile(MultipartFile file) throws IOException {
        File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convFile)) {
            fos.write(file.getBytes());
        }
        return convFile;
    }
}
