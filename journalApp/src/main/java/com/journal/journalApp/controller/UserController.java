package com.journal.journalApp.controller;

import com.journal.journalApp.entity.JournalEntry;
import com.journal.journalApp.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")

public class JournalEntryControllerv2 {

    @Autowired
    private JournalEntryService journalEntryService;

    @GetMapping
    public ResponseEntity<List<JournalEntry>> getAll() {
        List<JournalEntry> entries = journalEntryService.getAll();
        if (entries.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 No Content
        }
        return ResponseEntity.ok(entries); // 200 OK
    }

    @PostMapping
    public ResponseEntity<JournalEntry> add(@RequestBody JournalEntry entry) {
        entry.setDate(LocalDateTime.now());
        JournalEntry savedEntry = journalEntryService.saveEntry(entry);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEntry); // 201 Created
    }

    @GetMapping("/id/{myId}")
    public ResponseEntity<JournalEntry> getById(@PathVariable ObjectId myId) {
        Optional<JournalEntry> entry = journalEntryService.getById(myId);

        return entry.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/id/{myId}")
    public ResponseEntity<Void> deleteById(@PathVariable ObjectId myId) {
        Optional<JournalEntry> entry = journalEntryService.getById(myId);
        if (entry.isPresent()) {
            journalEntryService.deleteById(myId);
            return ResponseEntity.noContent().build(); // 204 No Content
        } else {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }

    @PutMapping("/id/{myId}")
    public ResponseEntity<JournalEntry> updateById(@PathVariable ObjectId myId,
                                                   @RequestBody JournalEntry newEntry) {
        Optional<JournalEntry> temp = journalEntryService.getById(myId);

        if (temp.isPresent()) {
            JournalEntry existingEntry = temp.get();
            existingEntry.setTitle(newEntry.getTitle());
            existingEntry.setContent(newEntry.getContent());
            existingEntry.setDate(newEntry.getDate());

            JournalEntry updated = journalEntryService.saveEntry(existingEntry);
            return ResponseEntity.ok(updated); // 200 OK
        } else {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }
}
