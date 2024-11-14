package com.example.profileVerse.service;

import com.example.profileVerse.entity.Batch;
import java.util.List;
import java.util.Optional;

public interface BatchService {
    List<Batch> getAllBatches();

    Optional<Batch> getBatchById(Long id);

    Batch createBatch(Batch batch);

    Batch updateBatch(Long id, Batch batch);

    Batch patchBatch(Long id, Batch batch);

    void deleteBatch(Long id);
}
