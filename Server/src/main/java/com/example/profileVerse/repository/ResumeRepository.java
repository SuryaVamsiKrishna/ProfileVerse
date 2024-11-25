package com.example.profileVerse.repository;

import com.example.profileVerse.entity.Resume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResumeRepository extends JpaRepository<Resume, Long> {

    /**
     * Fetch all resumes associated with a given batch ID.
     *
     * @param batchId the ID of the batch
     * @return List of resumes belonging to the batch
     */
    @Query("SELECT r FROM Resume r WHERE r.batchId = :batchId")
    List<Resume> findByBatchId(@Param("batchId") Long batchId);

    /**
     * Fetch all resume text content for analysis.
     *
     * @return List of resume text content strings
     */
    @Query("SELECT r.resumeText FROM Resume r")
    List<String> findAllResumeTexts();

    /**
     * Fetch resume text content by batch ID for specific batch analysis.
     *
     * @param batchId the ID of the batch
     * @return List of resume text content strings for the batch
     */
    @Query("SELECT r.resumeText FROM Resume r WHERE r.batchId = :batchId")
    List<String> findResumeTextsByBatchId(@Param("batchId") Long batchId);
}
