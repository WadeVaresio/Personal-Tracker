package com.varesio.wade.personaltracker.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.varesio.wade.personaltracker.entities.SavedLink;
import com.varesio.wade.personaltracker.repositories.SavedLinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/savedLinks")
public class SavedLinkController {

    @Autowired
    private SavedLinkRepository savedLinkRepository;

    @Autowired
    private ObjectMapper mapper;

    @PostMapping(value="/new")
    public ResponseEntity<String> newReminder(@RequestBody SavedLink savedLink) throws IOException {
        SavedLink saved = savedLinkRepository.save(savedLink);

        String mapped = mapper.writeValueAsString(saved);
        return ResponseEntity.ok().body(mapped);
    }

    @GetMapping(value="/all/{id}")
    public ResponseEntity<String> getAllByUserId(@RequestParam String userId) throws IOException{
        List<SavedLink> results = savedLinkRepository.findAllByUserId(userId);

        return ResponseEntity.ok().body(mapper.writeValueAsString(results));
    }

    @GetMapping("/all")
    public ResponseEntity<String> getAllSavedLinks() throws IOException {
        return ResponseEntity.ok().body(mapper.writeValueAsString(savedLinkRepository.findAll()));
    }
}
