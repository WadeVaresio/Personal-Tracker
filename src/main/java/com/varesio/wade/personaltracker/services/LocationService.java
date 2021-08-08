package com.varesio.wade.personaltracker.services;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Objects;

import org.json.JSONObject;

@Service
public class LocationService {
    private final String BASE_URI = "https://nominatim.openstreetmap.org/search";

    @Autowired
    @Lazy
    private  RestTemplate restTemplate;

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
