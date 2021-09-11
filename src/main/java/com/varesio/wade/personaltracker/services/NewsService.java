package com.varesio.wade.personaltracker.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NewsService {
    @Value("${news.api.key}")
    private String API_KEY;

    private final String BASE_URI = "https://newsapi.org/v2/";

    private final RestTemplate restTemplate;

    public NewsService(RestTemplateBuilder restTemplateBuilder){
        restTemplate = restTemplateBuilder.build();
    }

    public String getTopHeadlines(){
        String request = String.format("top-headlines?country=us&apiKey=%s", API_KEY);
        String url = BASE_URI + request;

        return restTemplate.getForObject(url, String.class);
    }

}
