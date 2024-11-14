package com.example.profileVerse.controller;

import com.example.profileVerse.entity.Batch;
import com.example.profileVerse.service.BatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/batch")
@CrossOrigin(origins = "http://localhost:3000")
public class BatchController {

    @Autowired
    private BatchService batchService;

    @GetMapping
    public ResponseEntity<List<Batch>> getAllBatches() {
        return new ResponseEntity<>(batchService.getAllBatches(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Batch> getBatchById(@PathVariable Long id) {
        return batchService.getBatchById(id)
                .map(batch -> new ResponseEntity<>(batch, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Batch> createBatch(@RequestBody Batch batch) {
        return new ResponseEntity<>(batchService.createBatch(batch), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Batch> updateBatch(@PathVariable Long id, @RequestBody Batch batch) {
        return new ResponseEntity<>(batchService.updateBatch(id, batch), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Batch> patchBatch(@PathVariable Long id, @RequestBody Batch batch) {
        return new ResponseEntity<>(batchService.patchBatch(id, batch), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBatch(@PathVariable Long id) {
        batchService.deleteBatch(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
