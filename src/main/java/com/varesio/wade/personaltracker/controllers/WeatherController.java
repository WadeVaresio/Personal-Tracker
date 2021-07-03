package com.varesio.wade.personaltracker.controllers;

import com.varesio.wade.personaltracker.services.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {
    @Autowired
    private WeatherService weatherService;

    @GetMapping(value = "", produces = "application/json")
    public ResponseEntity<String> getForecastWeather(@RequestParam(required = true) String location){
        return ResponseEntity.ok().body(weatherService.getWeather(location));
    }
}
