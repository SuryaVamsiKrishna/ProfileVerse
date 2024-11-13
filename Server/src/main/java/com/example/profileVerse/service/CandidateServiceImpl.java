package com.example.profileVerse.service;

import com.example.profileVerse.entity.Candidate;
import com.example.profileVerse.repository.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CandidateServiceImpl implements CandidateService {

    @Autowired
    private CandidateRepository candidateRepository;

    // Fetch all candidates
    @Override
    public List<Candidate> getAllCandidates() {
        return candidateRepository.findAll();
    }

    // Fetch a candidate by ID
    @Override
    public Candidate getCandidateById(Long id) {
        return candidateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Candidate not found with ID: " + id));
    }

    // Save a new candidate
    @Override
    public Candidate saveCandidate(Candidate candidate) {
        return candidateRepository.save(candidate);
    }

    // Update an existing candidate
    @Override
    public Candidate updateCandidate(Long id, Candidate candidate) {
        Candidate existingCandidate = getCandidateById(id);

        // Update fields
        existingCandidate.setFirstName(candidate.getFirstName());
        existingCandidate.setLastName(candidate.getLastName());
        existingCandidate.setYearsOfExperience(candidate.getYearsOfExperience());
        // existingCandidate.setResumeData(candidate.getResumeData());
        // existingCandidate.setResumeText(candidate.getResumeText());
        existingCandidate.setResumeMatchingScore(candidate.getResumeMatchingScore());
        existingCandidate.setGithubMatchingScore(candidate.getGithubMatchingScore());
        existingCandidate.setFinalScore(candidate.getFinalScore());

        return candidateRepository.save(existingCandidate);
    }

    // Delete a candidate by ID
    @Override
    public void deleteCandidate(Long id) {
        candidateRepository.deleteById(id);
    }
}
