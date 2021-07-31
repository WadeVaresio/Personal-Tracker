package com.varesio.wade.personaltracker.services;

import org.json.JSONArray;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Objects;

import org.json.JSONObject;

@Service
public class LocationService {
    private final String BASE_URI = "https://nominatim.openstreetmap.org/search";

    private final RestTemplate restTemplate;

    public LocationService(RestTemplateBuilder restTemplateBuilder){
        restTemplate = restTemplateBuilder.build();
    }

    public HashMap<String, String> getCoordinates(String location){
        String params = String.format("/?q=%s&format=json", location);

        String url = BASE_URI + params;

        JSONArray response = new JSONArray(Objects.requireNonNull(restTemplate.getForObject(url, String.class)));
        JSONObject closest = (JSONObject) response.get(0);

        HashMap<String, String> coordinates = new HashMap<>();
        coordinates.put("lat", (String) closest.get("lat"));
        coordinates.put("lon", (String) closest.get("lon"));
        return coordinates;
    }
}
