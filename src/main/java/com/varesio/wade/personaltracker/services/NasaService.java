package com.varesio.wade.personaltracker.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NasaService {

    @Value("${nasa.api.key}")
    private String NASA_API_KEY;

    @Autowired
    @Lazy
    private  RestTemplate restTemplate;

    private final String BASE_URI = "https://api.nasa.gov/planetary/earth/imagery";

    public byte[] getSatelliteImage(String lat, String lon){
        String dim = "0.3";
        String params = String.format("?lon=%s&lat=%s&dim=%s&api_key=%s", lon, lat, dim, NASA_API_KEY);

        String url = BASE_URI + params;

        return restTemplate.getForObject(url, byte[].class);
    }
}
