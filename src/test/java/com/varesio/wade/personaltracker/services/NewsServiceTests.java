package com.varesio.wade.personaltracker.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.client.MockRestServiceServer;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest(NewsService.class)
public class NewsServiceTests {
    @Autowired
    private MockRestServiceServer mockRestServiceServer;

    @Autowired
    private NewsService newsService;

    @Test
    public void test_getTopHeadlines(){
        ReflectionTestUtils.setField(newsService, "API_KEY", "123");
        String expectedURL = "https://newsapi.org/v2/top-headlines?country=us&apiKey=123";

        String fakeJSON = " { news: null } ";

        mockRestServiceServer.expect(requestTo(expectedURL))
                .andRespond(withSuccess(fakeJSON, MediaType.APPLICATION_JSON));

        String actual = newsService.getTopHeadlines();

        assertEquals(fakeJSON, actual);
    }
}
