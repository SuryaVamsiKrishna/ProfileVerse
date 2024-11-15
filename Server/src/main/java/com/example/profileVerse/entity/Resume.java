package com.example.profileVerse.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "resumes")
public class Resume {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long resumeId;

    // Many resumes can belong to one batch
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "batch_id", nullable = false)
    private Batch batch;

    @Lob
    @Column(name = "resume_file", columnDefinition = "BYTEA")
    private byte[] resumeFile;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime updatedAt = LocalDateTime.now();

    // Constructors
    public Resume() {
    }

    public Resume(Batch batch, byte[] resumeFile) {
        this.batch = batch;
        this.resumeFile = resumeFile;
    }

    // Getters and Setters
    public Long getResumeId() {
        return resumeId;
    }

    public void setResumeId(Long resumeId) {
        this.resumeId = resumeId;
    }

    public Batch getBatch() {
        return batch;
    }

    public void setBatch(Batch batch) {
        this.batch = batch;
    }

    public byte[] getResumeFile() {
        return resumeFile;
    }

    public void setResumeFile(byte[] resumeFile) {
        this.resumeFile = resumeFile;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
