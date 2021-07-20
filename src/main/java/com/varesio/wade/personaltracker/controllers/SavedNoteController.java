package com.varesio.wade.personaltracker.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.varesio.wade.personaltracker.entities.SavedNote;
import com.varesio.wade.personaltracker.models.User;
import com.varesio.wade.personaltracker.repositories.SavedNoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/api/private/savedNotes")
public class SavedNoteController {
    @Value("${auth0.audience}")
    private String audience;

    @Autowired
    private SavedNoteRepository savedNoteRepository;

    @Autowired
    private ObjectMapper mapper;

    @PostMapping(value="/new")
    public ResponseEntity<String> newNote(@RequestBody SavedNote savedNote, @RequestHeader("Authorization") String authorization) throws IOException {
        if(savedNote.getUserID().isEmpty())
            return ResponseEntity.badRequest().build();

        User user = new User(authorization, audience);

        if(!user.getEmail().equals(savedNote.getUserID()))
            return ResponseEntity.badRequest().build();

        if(savedNote.getNote().isEmpty())
            return ResponseEntity.badRequest().build();

        SavedNote saved = savedNoteRepository.save(savedNote);

        String mapped = mapper.writeValueAsString(saved);
        return ResponseEntity.ok().body(mapped);
    }

    @GetMapping("/get")
    public ResponseEntity<String> getAllSavedNotes(@RequestHeader("Authorization") String authorization) throws IOException {
        User user = new User(authorization, audience);

        return ResponseEntity.ok().body(mapper.writeValueAsString(savedNoteRepository.findAllByUserID(user.getEmail())));
    }

    @PutMapping(value = "/put", produces = "application/json")
    public ResponseEntity<String> editNote(@RequestBody SavedNote editedNote, @RequestHeader("Authorization") String authorization) throws JsonProcessingException {
        User user = new User(authorization, audience);

        if(!user.getEmail().equals(editedNote.getUserID()))
            return ResponseEntity.badRequest().build();

        Optional<SavedNote> searchResult = savedNoteRepository.findById(editedNote.getId());

        if(searchResult.isEmpty())
            return ResponseEntity.notFound().build();

        if(!searchResult.get().getUserID().equals(user.getEmail()))
            return ResponseEntity.badRequest().build();

        savedNoteRepository.save(editedNote);
        return ResponseEntity.ok().body(mapper.writeValueAsString(editedNote));
    }

    @DeleteMapping(value = "/delete", produces="application/json")
    public ResponseEntity<String> deleteNote(@RequestBody SavedNote deleteNote, @RequestHeader("Authorization") String authorization){
        User user = new User(authorization, audience);

        if(!user.getEmail().equals(deleteNote.getUserID()))
            return ResponseEntity.badRequest().build();

        Optional<SavedNote> savedNote = savedNoteRepository.findById(deleteNote.getId());

        if(savedNote.isEmpty() || !savedNote.equals(Optional.of(deleteNote)))
            return ResponseEntity.badRequest().build();

        if(!savedNote.get().getUserID().equals(user.getEmail()))
            return ResponseEntity.badRequest().build();

        savedNoteRepository.delete(deleteNote);
        return ResponseEntity.noContent().build();
    }
}
