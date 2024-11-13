package com.example.profileVerse.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "candidate")
public class Candidate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long candidateId;

    @ManyToOne
    @JoinColumn(name = "batch_id", nullable = false)
    private Batch batch;

    private String firstName;
    private String lastName;
    private Float yearsOfExperience;

    private Float resumeMatchingScore;
    private Float githubMatchingScore;
    private Float finalScore;

    private LocalDateTime processedAt;

    // Getters and Setters
    public Long getCandidateId() { return candidateId; }
    public void setCandidateId(Long candidateId) { this.candidateId = candidateId; }

    public Batch getBatch() { return batch; }
    public void setBatch(Batch batch) { this.batch = batch; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public Float getYearsOfExperience() { return yearsOfExperience; }
    public void setYearsOfExperience(Float yearsOfExperience) { this.yearsOfExperience = yearsOfExperience; }

    public Float getResumeMatchingScore() { return resumeMatchingScore; }
    public void setResumeMatchingScore(Float resumeMatchingScore) { this.resumeMatchingScore = resumeMatchingScore; }

    public Float getGithubMatchingScore() { return githubMatchingScore; }
    public void setGithubMatchingScore(Float githubMatchingScore) { this.githubMatchingScore = githubMatchingScore; }

    public Float getFinalScore() { return finalScore; }
    public void setFinalScore(Float finalScore) { this.finalScore = finalScore; }

    public LocalDateTime getProcessedAt() { return processedAt; }
    public void setProcessedAt(LocalDateTime processedAt) { this.processedAt = processedAt; }
}
