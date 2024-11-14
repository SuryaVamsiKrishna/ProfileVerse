package com.example.profileVerse.service;

import com.example.profileVerse.entity.Batch;
import com.example.profileVerse.repository.BatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BatchServiceImpl implements BatchService {

    @Autowired
    private BatchRepository batchRepository;

    @Override
    public List<Batch> getAllBatches() {
        return batchRepository.findAll();
    }

    @Override
    public Optional<Batch> getBatchById(Long id) {
        return batchRepository.findById(id);
    }

    @Override
    public Batch createBatch(Batch batch) {
        batch.setCreatedAt(LocalDateTime.now());
        batch.setUpdatedAt(LocalDateTime.now());
        return batchRepository.save(batch);
    }

    @Override
    public Batch updateBatch(Long id, Batch batch) {
        Optional<Batch> batchOptional = batchRepository.findById(id);
        if (batchOptional.isPresent()) {
            Batch existingBatch = batchOptional.get();
            existingBatch.setSeason(batch.getSeason());
            existingBatch.setYear(batch.getYear());
            existingBatch.setRole(batch.getRole());
            existingBatch.setJobDescription(batch.getJobDescription());
            existingBatch.setUpdatedAt(LocalDateTime.now());
            return batchRepository.save(existingBatch);
        } else {
            throw new RuntimeException("Batch not found with ID: " + id);
        }
    }

    @Override
    public Batch patchBatch(Long id, Batch batch) {
        Optional<Batch> batchOptional = batchRepository.findById(id);
        if (batchOptional.isPresent()) {
            Batch existingBatch = batchOptional.get();

            // Only update fields that are not null
            if (batch.getSeason() != null) {
                existingBatch.setSeason(batch.getSeason());
            }
            if (batch.getYear() != 0) {
                existingBatch.setYear(batch.getYear());
            }
            if (batch.getRole() != null) {
                existingBatch.setRole(batch.getRole());
            }
            if (batch.getJobDescription() != null) {
                existingBatch.setJobDescription(batch.getJobDescription());
            }

            existingBatch.setUpdatedAt(LocalDateTime.now());
            return batchRepository.save(existingBatch);
        } else {
            throw new RuntimeException("Batch not found with ID: " + id);
        }
    }

    @Override
    public void deleteBatch(Long id) {
        if (batchRepository.existsById(id)) {
            batchRepository.deleteById(id);
        } else {
            throw new RuntimeException("Batch not found with ID: " + id);
        }
    }
}
