package com.varesio.wade.personaltracker.controllers;

import com.varesio.wade.personaltracker.services.NasaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;


@RestController
@RequestMapping("/api/nasa")
public class NasaController {
    @Autowired
    private NasaService nasaService;

    @GetMapping(value = "/satImage/Goleta")
    public  ResponseEntity<byte[]> getGoletaSatelliteImage() throws IOException {
        byte[] base64encodedData = nasaService.getGoletaSatelliteImage();
        return ResponseEntity.ok().body(base64encodedData);
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
