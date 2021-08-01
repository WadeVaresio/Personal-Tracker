package com.varesio.wade.personaltracker.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.client.MockRestServiceServer;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest(NasaService.class)
public class NasaServiceTests {
    @Autowired
    private MockRestServiceServer mockRestServiceServer;

    @Autowired
    private NasaService nasaService;

    @Test
    public void test_getSatelliteImage(){
        ReflectionTestUtils.setField(nasaService, "NASA_API_KEY", "99"); // mock NASA weather API key
        String expectedURL = "https://api.nasa.gov/planetary/earth/imagery?lon=123&lat=123&dim=0.3&api_key=99";

        byte[] fakeResult = {1, 2, 3};

        mockRestServiceServer.expect(requestTo(expectedURL))
                .andRespond(withSuccess(fakeResult, MediaType.IMAGE_PNG));

        byte [] response = nasaService.getSatelliteImage("123", "123");
        assertArrayEquals(fakeResult, response);
    }

}
