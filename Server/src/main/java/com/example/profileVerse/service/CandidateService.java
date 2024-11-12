package com.example.profileVerse.service;

import com.example.profileVerse.entity.Candidate;
import java.util.List;

public interface CandidateService {
    List<Candidate> getAllCandidates();
    Candidate getCandidateById(Long id);
    Candidate saveCandidate(Candidate candidate);
    Candidate updateCandidate(Long id, Candidate candidate);
    void deleteCandidate(Long id);
}
