package com.example.profileVerse.service;

import com.example.profileVerse.entity.Candidate;
import com.example.profileVerse.repository.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CandidateServiceImpl implements CandidateService {

    @Autowired
    private CandidateRepository candidateRepository;

    @Override
    public List<Candidate> getAllCandidates() {
    List<Candidate> candidates = candidateRepository.findAll();
    // candidates.forEach(candidate -> {
    //     byte[] githubDataJson = candidate.getGithubData();
    //     System.out.println("GitHub Data: " + githubDataJson);
    // });
    return candidates;
}
    @Override
    public Candidate getCandidateById(Long id) {
        return candidateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Candidate not found with ID: " + id));
    }

    @Override
    public Candidate saveCandidate(Candidate candidate) {
        return candidateRepository.save(candidate);
    }

    @Override
    public Candidate updateCandidate(Long id, Candidate candidate) {
        Candidate existingCandidate = getCandidateById(id);
        existingCandidate.setFirstName(candidate.getFirstName());
        existingCandidate.setLastName(candidate.getLastName());
        existingCandidate.setYearsOfExperience(candidate.getYearsOfExperience());
        existingCandidate.setResumeData(candidate.getResumeData());
        existingCandidate.setResumeText(candidate.getResumeText());
        existingCandidate.setGithubData(candidate.getGithubData());
        existingCandidate.setResumeMatchingScore(candidate.getResumeMatchingScore());
        existingCandidate.setGithubMatchingScore(candidate.getGithubMatchingScore());
        existingCandidate.setFinalScore(candidate.getFinalScore());
        return candidateRepository.save(existingCandidate);
    }

    @Override
    public void deleteCandidate(Long id) {
        candidateRepository.deleteById(id);
    }


}
