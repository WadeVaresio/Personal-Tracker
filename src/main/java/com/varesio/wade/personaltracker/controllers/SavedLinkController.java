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
@RequestMapping("/api/reminders/")
public class SavedLinkController {

    @Autowired
    private SavedLinkRepository savedLinkRepository;

    @Autowired
    private ObjectMapper mapper;

    @PostMapping(value="new")
    public ResponseEntity<String> newReminder(@RequestParam String link,
                                              @RequestParam String note) throws IOException {
        SavedLink reminder = new SavedLink(null, link, note);
        SavedLink saved = savedLinkRepository.save(reminder);

        String mapped = mapper.writeValueAsString(saved);
        return ResponseEntity.ok().body(mapped);
    }

    @GetMapping(value="all/{id}")
    public ResponseEntity<String> getAllByUserId(@RequestParam String userId) throws IOException{
        List<SavedLink> results = savedLinkRepository.findAllByUserId(userId);

        return ResponseEntity.ok().body(mapper.writeValueAsString(results));
    }
}
