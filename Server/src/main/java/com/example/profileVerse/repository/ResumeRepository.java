package com.example.profileVerse.repository;

import com.example.profileVerse.entity.Resume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResumeRepository extends JpaRepository<Resume, Long> {
    List<Resume> findByBatch_BatchId(Long batchId);
}
