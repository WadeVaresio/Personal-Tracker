package com.varesio.wade.personaltracker.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.varesio.wade.personaltracker.entities.Reminder;
import com.varesio.wade.personaltracker.repositories.ReminderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/reminders/")
public class ReminderController {

    @Autowired
    private ReminderRepository reminderRepository;

    @Autowired
    private ObjectMapper mapper;

    @PostMapping(value="new")
    public ResponseEntity<String> newReminder(@RequestParam String link,
                                              @RequestParam String note) throws IOException {
        Reminder reminder = new Reminder(null, link, note);
        Reminder saved = reminderRepository.save(reminder);

        String mapped = mapper.writeValueAsString(saved);
        return ResponseEntity.ok().body(mapped);
    }

    @GetMapping(value="all/{id}")
    public ResponseEntity<String> getAllByUserId(@RequestParam String userId) throws IOException{
        List<Reminder> results = reminderRepository.findAllByUserId(userId);

        return ResponseEntity.ok().body(mapper.writeValueAsString(results));
    }
}
