package com.varesio.wade.personaltracker.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.varesio.wade.personaltracker.entities.Deadline;
import com.varesio.wade.personaltracker.models.User;
import com.varesio.wade.personaltracker.repositories.DeadlineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/private/deadlines")
public class DeadlineController {
    @Value("${auth0.audience}")
    private String jwtAudience;

    @Autowired
    private DeadlineRepository deadlineRepository;

    @Autowired
    private ObjectMapper mapper;

    @PostMapping("/new")
    public ResponseEntity<String> newDeadline(@RequestBody Deadline deadline, @RequestHeader("Authorization") String authorization) throws Exception{
        if(deadline.getUserID().isEmpty())
            return ResponseEntity.badRequest().build();

        User user = new User(authorization, jwtAudience);

        if(!user.getEmail().equals(deadline.getUserID()))
            return ResponseEntity.badRequest().build();

        if(deadline.getDescription().isEmpty())
            return ResponseEntity.badRequest().build();

        Deadline saved = deadlineRepository.save(deadline);

        return ResponseEntity.ok().body(mapper.writeValueAsString(saved));
    }

    @GetMapping("/get")
    public ResponseEntity<String> getDeadlines(@RequestHeader("Authorization") String authorization) throws Exception{
        User user = new User(authorization, jwtAudience);

        return ResponseEntity.ok().body(mapper.writeValueAsString(deadlineRepository.findAllByUserID(user.getEmail())));
    }

    @PutMapping("/put")
    public ResponseEntity<String> editDeadline(@RequestBody Deadline deadline, @RequestHeader("Authorization") String authorization) throws Exception{
        User user = new User(authorization, jwtAudience);

        if(!user.getEmail().equals(deadline.getUserID()))
            return ResponseEntity.badRequest().build();

        Optional<Deadline> searchResult = deadlineRepository.findById(deadline.getId());

        if(searchResult.isEmpty())
            return ResponseEntity.badRequest().build();

        if(!searchResult.get().getUserID().equals(user.getEmail()))
            return ResponseEntity.badRequest().build();

        deadlineRepository.save(deadline);

        return ResponseEntity.ok().body(mapper.writeValueAsString(deadline));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteDeadline(@RequestBody Deadline deadline, @RequestHeader("Authorization") String authorization) throws Exception{
        User user = new User(authorization, jwtAudience);

        if(!user.getEmail().equals(deadline.getUserID()))
            return ResponseEntity.badRequest().build();

        Optional<Deadline> savedDeadline = deadlineRepository.findById(deadline.getId());

        if(savedDeadline.isEmpty())
            return ResponseEntity.badRequest().build();

        if(!savedDeadline.get().getUserID().equals(user.getEmail()))
            return ResponseEntity.badRequest().build();

        deadlineRepository.delete(deadline);
        return ResponseEntity.noContent().build();
    }

}
