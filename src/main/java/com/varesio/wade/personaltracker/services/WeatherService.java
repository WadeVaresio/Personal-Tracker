package com.varesio.wade.personaltracker.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    @Value("${weather.api.key}")
    private String API_KEY;

    private final String BASE_URI = "http://api.weatherapi.com/v1/";

    @Autowired
    @Lazy
    private  RestTemplate restTemplate;

    public String getWeather(String location){
        String aqi = "yes";
        String alerts = "yes";
        String days = "3"; // Can only do 3 days with free plan
        String params = String.format("?key=%s&q=%s&days=%s&aqi=%s&alerts=%s", API_KEY, location, days, aqi, alerts);

        String url = BASE_URI + "forecast.json" + params;

        return restTemplate.getForObject(url, String.class);
    }
}
