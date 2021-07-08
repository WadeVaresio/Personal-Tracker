package com.varesio.wade.personaltracker.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @GetMapping("/loggedIn")
    public ResponseEntity<String> isLoggedIn(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if(auth.getPrincipal().equals("anonymousUser")) // not logged in
            return ResponseEntity.ok().body("false");

        return ResponseEntity.ok().body("true");
    }
}
