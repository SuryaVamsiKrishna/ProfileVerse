package com.example.profileVerse.repository;

import com.example.profileVerse.entity.Resume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResumeRepository extends JpaRepository<Resume, Long> {

    @Query("SELECT r FROM Resume r WHERE r.batch.batchId = :batchId")
    List<Resume> findByBatchId(@Param("batchId") Long batchId);
}
