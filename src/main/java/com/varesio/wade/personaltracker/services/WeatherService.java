package com.varesio.wade.personaltracker.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    @Value("${weather-api-key}")
    private String API_KEY;

    private final String BASE_URI = "http://api.weatherapi.com/v1/current.json";

    public String getGoletaWeather(){
        String location = "goleta";
        String aqi = "no";
        String params = String.format("?key=%s&q=%s&aqi=%s", API_KEY, location, aqi);

        String url = BASE_URI + params;

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, String.class);
    }
}
