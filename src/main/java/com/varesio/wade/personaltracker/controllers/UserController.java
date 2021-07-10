package com.varesio.wade.personaltracker.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @GetMapping("/loggedIn")
    public ResponseEntity<String> isLoggedIn(@AuthenticationPrincipal OidcUser user){
        if(user != null)
            return ResponseEntity.ok().body("true");
        return ResponseEntity.ok().body("false");
    }
}
