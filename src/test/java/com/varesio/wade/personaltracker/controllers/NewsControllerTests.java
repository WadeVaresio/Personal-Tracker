package com.varesio.wade.personaltracker.controllers;

import com.varesio.wade.personaltracker.services.NewsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = NewsController.class)
public class NewsControllerTests {
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private NewsService mockNewsService;

    private final String GET_TOP_HEADLINES = "/api/public/news/get/topHeadlines";

    @BeforeEach
    public void setup(){
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void testGetTopHeadlines() throws Exception{
        String fakeNews = " { news }";

        when(mockNewsService.getTopHeadlines()).thenReturn(fakeNews);

        MvcResult response = mockMvc.perform(get(GET_TOP_HEADLINES).with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        String responseJSON = response.getResponse().getContentAsString();
        assertEquals(fakeNews, responseJSON);
    }
}
