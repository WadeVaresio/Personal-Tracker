package com.varesio.wade.personaltracker.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.varesio.wade.personaltracker.entities.SavedNote;
import com.varesio.wade.personaltracker.repositories.SavedNoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/savedNotes")
public class SavedNoteController {

    @Autowired
    private SavedNoteRepository savedNoteRepository;

    @Autowired
    private ObjectMapper mapper;

    @PostMapping(value="/new")
    public ResponseEntity<String> newNote(@RequestBody SavedNote savedNote) throws IOException {
        SavedNote saved = savedNoteRepository.save(savedNote);

        String mapped = mapper.writeValueAsString(saved);
        return ResponseEntity.ok().body(mapped);
    }

    @GetMapping("/all")
    public ResponseEntity<String> getAllSavedNotes() throws IOException {
        return ResponseEntity.ok().body(mapper.writeValueAsString(savedNoteRepository.findAll()));
    }

    @PutMapping("/put")
    public ResponseEntity<String> editNoteById(@RequestBody SavedNote editedNote) throws JsonProcessingException {
        Optional<SavedNote> searchResult = savedNoteRepository.findById(editedNote.getId());

        if(searchResult.isEmpty())
            return ResponseEntity.notFound().build();

        savedNoteRepository.save(editedNote);
        return ResponseEntity.ok().body(mapper.writeValueAsString(editedNote));
    }
}
