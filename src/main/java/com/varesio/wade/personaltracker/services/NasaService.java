package com.varesio.wade.personaltracker.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NasaService {

    @Value("${nasa.api.key}")
    private String NASA_API_KEY;

    private final String BASE_URI = "https://api.nasa.gov/planetary/earth/imagery";

    public byte[] getGoletaSatelliteImage(){
        String latitude = "34.42173989989971";
        String longitude = "-119.87647933801838";
        String dim = "0.5";
        String params = String.format("?lon=%s&lat=%s&dim=%s&api_key=%s", longitude, latitude, dim, NASA_API_KEY);

        String url = BASE_URI + params;
        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.getForObject(url, byte[].class);
    }
}
