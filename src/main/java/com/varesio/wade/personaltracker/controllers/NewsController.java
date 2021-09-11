package com.varesio.wade.personaltracker.controllers;

import com.varesio.wade.personaltracker.services.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public/news")
public class NewsController {
    @Autowired
    private NewsService newsService;

    @GetMapping("/get/topHeadlines")
    public ResponseEntity<String> getTopHeadlines(){
        return ResponseEntity.ok(newsService.getTopHeadlines());
    }
}
