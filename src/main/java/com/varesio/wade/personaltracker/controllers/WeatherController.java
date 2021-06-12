package com.varesio.wade.personaltracker.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.varesio.wade.personaltracker.services.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {
    @Autowired
    private WeatherService weatherService;

    @GetMapping(value="/goleta", produces = "application/json")
    public ResponseEntity<String> getGoletaWeather() throws JsonProcessingException {
        return ResponseEntity.ok().body(weatherService.getGoletaWeather());
    }
}
