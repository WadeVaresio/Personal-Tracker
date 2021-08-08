package com.varesio.wade.personaltracker.controllers;

import com.varesio.wade.personaltracker.services.LocationService;
import com.varesio.wade.personaltracker.services.NasaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;


@RestController
@RequestMapping("/api/nasa")
public class NasaController {
    @Autowired
    private NasaService nasaService;

    @Autowired
    private LocationService locationService;

    @GetMapping(value="/satImage/get")
    public ResponseEntity<byte[]> getSatelliteImage(@RequestParam("location") String location){
        HashMap<String, String> coordinates = locationService.getCoordinates(location);

        return ResponseEntity.ok().body(nasaService.getSatelliteImage(coordinates.get("lat"), coordinates.get("lon")));
    }

    @Bean
    public RestTemplate restTemplate(List<HttpMessageConverter<?>> messageConverters) {
        return new RestTemplate(messageConverters);
    }

    @Bean
    public ByteArrayHttpMessageConverter byteArrayHttpMessageConverter(){
        return new ByteArrayHttpMessageConverter();
    }
}
