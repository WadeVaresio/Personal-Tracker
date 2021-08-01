package com.varesio.wade.personaltracker.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.client.MockRestServiceServer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest(WeatherService.class)
public class WeatherServiceTests {

    @Autowired
    private MockRestServiceServer mockRestServiceServer;

    @Autowired
    private WeatherService weatherService;


    @Test
    public void test_getWeather(){
        ReflectionTestUtils.setField(weatherService, "API_KEY", "123"); // mock the weather API key
        String expectedURL = "http://api.weatherapi.com/v1/forecast.json?key=123&q=Goleta&days=3&aqi=yes&alerts=yes";

        String fakeJSON = " { temp: 0 }";

        mockRestServiceServer.expect(requestTo(expectedURL))
                .andRespond(withSuccess(fakeJSON, MediaType.APPLICATION_JSON));

        String actual = weatherService.getWeather("Goleta");

        assertEquals(fakeJSON, actual);
    }
}
