package com.varesio.wade.personaltracker.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.varesio.wade.personaltracker.entities.SavedNote;
import com.varesio.wade.personaltracker.repositories.SavedNoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/savedNotes")
public class SavedNoteController {

    @Autowired
    private SavedNoteRepository savedNoteRepository;

    @Autowired
    private ObjectMapper mapper;

    @PostMapping(value="/new")
    public ResponseEntity<String> newReminder(@RequestBody SavedNote savedNote) throws IOException {
        SavedNote saved = savedNoteRepository.save(savedNote);

        String mapped = mapper.writeValueAsString(saved);
        return ResponseEntity.ok().body(mapped);
    }

    @GetMapping(value="/all/{id}")
    public ResponseEntity<String> getAllByUserId(@RequestParam String userId) throws IOException{
        List<SavedNote> results = savedNoteRepository.findAllByUserId(userId);

        return ResponseEntity.ok().body(mapper.writeValueAsString(results));
    }

    @GetMapping("/all")
    public ResponseEntity<String> getAllSavedNotes() throws IOException {
        return ResponseEntity.ok().body(mapper.writeValueAsString(savedNoteRepository.findAll()));
    }
}
