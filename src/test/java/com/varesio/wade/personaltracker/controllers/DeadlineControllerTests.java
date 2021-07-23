package com.varesio.wade.personaltracker.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.varesio.wade.personaltracker.entities.Deadline;
import com.varesio.wade.personaltracker.repositories.DeadlineRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = DeadlineController.class)
public class DeadlineControllerTests {
    private final String testJWTToken = "Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6IlI0a3ZHbHoteExVVlJ3djFIdF91aSJ9.eyJodHRwczovL3BlcnNvbmFsLXRyYWNrZXItYXBpLmNvbSI6eyJlbWFpbCI6IndhZGV2YXJlc2lvQGdtYWlsLmNvbSIsImdpdmVuX25hbWUiOiJXYWRlIn0sImlzcyI6Imh0dHBzOi8vZGV2LWZlamZ1ODAyLnVzLmF1dGgwLmNvbS8iLCJzdWIiOiJnb29nbGUtb2F1dGgyfDExNjQxODE1NTE5MjYzODE3NzQ2MiIsImF1ZCI6WyJodHRwczovL3BlcnNvbmFsLXRyYWNrZXItYXBpLmNvbSIsImh0dHBzOi8vZGV2LWZlamZ1ODAyLnVzLmF1dGgwLmNvbS91c2VyaW5mbyJdLCJpYXQiOjE2MjY2NDExMDYsImV4cCI6MTYyNjcyNzUwNiwiYXpwIjoiUUpSSlF4VTc2SW9rMndOUkhscmd1Nmd3VVpVb1FTU1ciLCJzY29wZSI6Im9wZW5pZCBwcm9maWxlIGVtYWlsIn0.MsJqi5LDPgC69dZrKbZwIuzzoAhQ8q7jI6I2b7h3Qi2j7fxvEu5614SKJnUuAn0YuISu3UpLhjql-ccjyVX9-kr1DJ0ZZ5LZguuYV8UnKFTu1W_vJIzZf7PQBAMr78NbQbmzdPx3uQZHjVgHTdP0roBaQFB60pVG88iyy3-lMt7tA87N26RsSIdwQwSgLSGOoiFEoWZuxsiuvv3l_IslUfmA4qQuHTE82TiFlCqFhuIxer-muXXo6E7xekyp4seeg-Uny4E2xLurRQ15HDPnAYgPOfKILNgUlgG8G3Dl9wrCp_RAbQ6V4eHN0-VhkTF--HpfHJcMCtlEvzW8mX7k5g";

    private final Deadline testDeadline = new Deadline(1L, "wadevaresio@gmail.com", new Timestamp(0L), "test deadline");

    @MockBean
    private DeadlineRepository mockDeadlineRepository;

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper mapper;

    private final String POST_MAPPING = "/api/private/deadlines/new";
    private final String GET_MAPPING = "/api/private/deadlines/get";
    private final String PUT_MAPPING = "/api/private/deadlines/put";
    private final String DELETE_MAPPING = "/api/private/deadlines/delete";

    @BeforeEach
    public void setup(){
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void test_newDeadlineSuccessful() throws Exception{
        when(mockDeadlineRepository.save(testDeadline)).thenReturn(testDeadline);

        String requestBody = mapper.writeValueAsString(testDeadline);

        MvcResult responseEntity = mockMvc.perform(post(POST_MAPPING).with(csrf())
                                            .contentType(MediaType.APPLICATION_JSON)
                                            .header(HttpHeaders.AUTHORIZATION, testJWTToken)
                                            .content(requestBody))
                                    .andExpect(status().isOk()).andReturn();

        String response = responseEntity.getResponse().getContentAsString();
        Deadline responseDeadline = mapper.readValue(response, Deadline.class);
        assertEquals(testDeadline, responseDeadline);
        verify(mockDeadlineRepository, never()).findAllByUserID(any(String.class));
        verify(mockDeadlineRepository, times(1)).save(any(Deadline.class));
    }

    @Test
    public void test_newDeadlineEmptyUserID() throws Exception{
        testDeadline.setUserID("");

        String requestBody = mapper.writeValueAsString(testDeadline);

        mockMvc.perform(post(POST_MAPPING).with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, testJWTToken)
                        .content(requestBody))
                .andExpect(status().isBadRequest()).andReturn();

        verify(mockDeadlineRepository, never()).save(any(Deadline.class));
        verify(mockDeadlineRepository, never()).findAllByUserID(any(String.class));
    }

    @Test
    public void test_newDeadlineDiffEmail() throws Exception{
        testDeadline.setUserID("random");

        String requestBody = mapper.writeValueAsString(testDeadline);

        mockMvc.perform(post(POST_MAPPING).with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, testJWTToken)
                .content(requestBody))
                .andExpect(status().isBadRequest()).andReturn();

        verify(mockDeadlineRepository, never()).save(any(Deadline.class));
        verify(mockDeadlineRepository, never()).findAllByUserID(any(String.class));
    }

    @Test
    public void test_newDeadlineEmptyDescription() throws Exception{
        testDeadline.setDescription("");

        String requestBody = mapper.writeValueAsString(testDeadline);

        mockMvc.perform(post(POST_MAPPING).with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, testJWTToken)
                .content(requestBody))
                .andExpect(status().isBadRequest()).andReturn();

        verify(mockDeadlineRepository, never()).save(any(Deadline.class));
        verify(mockDeadlineRepository, never()).findAllByUserID(any(String.class));
    }

    @Test
    public void test_getDeadlines() throws Exception{
        when(mockDeadlineRepository.findAllByUserID("wadevaresio@gmail.com")).thenReturn(List.of(testDeadline));
        String expected = mapper.writeValueAsString(List.of(testDeadline));

        MvcResult responseEntity = mockMvc.perform(get(GET_MAPPING).with(csrf())
                                                    .contentType(MediaType.APPLICATION_JSON)
                                                    .header(HttpHeaders.AUTHORIZATION, testJWTToken))
                                    .andExpect(status().isOk()).andReturn();

        String response = responseEntity.getResponse().getContentAsString();

        assertEquals(expected, response);
        verify(mockDeadlineRepository, never()).save(any(Deadline.class));
        verify(mockDeadlineRepository, times(1)).findAllByUserID(any(String.class));
    }

    @Test
    public void test_deleteSuccessful() throws Exception{
        when(mockDeadlineRepository.findById(testDeadline.getId())).thenReturn(Optional.of(testDeadline));

        mockMvc.perform(delete(DELETE_MAPPING).with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, testJWTToken)
                        .content(mapper.writeValueAsString(testDeadline)))
                .andExpect(status().isNoContent()).andReturn();

        verify(mockDeadlineRepository, times(1)).findById(any(Long.class));
        verify(mockDeadlineRepository, times(1)).delete(any(Deadline.class));
        verify(mockDeadlineRepository, never()).save(any(Deadline.class));
    }

    @Test
    public void test_deleteBadUserID() throws Exception{
        testDeadline.setUserID("test");

        mockMvc.perform(delete(DELETE_MAPPING).with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, testJWTToken)
                .content(mapper.writeValueAsString(testDeadline)))
                .andExpect(status().isBadRequest()).andReturn();

        verify(mockDeadlineRepository, never()).findById(any(Long.class));
        verify(mockDeadlineRepository, never()).delete(any(Deadline.class));
        verify(mockDeadlineRepository, never()).save(any(Deadline.class));
    }

    @Test
    public void test_deleteEmptyDescription() throws Exception{
        testDeadline.setDescription("");

        mockMvc.perform(delete(DELETE_MAPPING).with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, testJWTToken)
                .content(mapper.writeValueAsString(testDeadline)))
                .andExpect(status().isBadRequest()).andReturn();

        verify(mockDeadlineRepository, times(1)).findById(any(Long.class));
        verify(mockDeadlineRepository, never()).delete(any(Deadline.class));
        verify(mockDeadlineRepository, never()).save(any(Deadline.class));
    }

    @Test
    public void test_deleteDiffUserID() throws Exception{
        Deadline deadlineDiff = new Deadline(1L, "test", new Timestamp(0), "description");

        when(mockDeadlineRepository.findById(testDeadline.getId())).thenReturn(Optional.of(deadlineDiff));

        mockMvc.perform(delete(DELETE_MAPPING).with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, testJWTToken)
                .content(mapper.writeValueAsString(testDeadline)))
                .andExpect(status().isBadRequest()).andReturn();

        verify(mockDeadlineRepository, times(1)).findById(any(Long.class));
        verify(mockDeadlineRepository, never()).delete(any(Deadline.class));
        verify(mockDeadlineRepository, never()).save(any(Deadline.class));
    }

    @Test
    public void test_putDeadlineSuccessful() throws Exception{
        when(mockDeadlineRepository.findById(testDeadline.getId())).thenReturn(Optional.of(testDeadline));

        String requestBody = mapper.writeValueAsString(testDeadline);

        MvcResult responseEntity = mockMvc.perform(put(PUT_MAPPING).with(csrf())
                                                    .contentType(MediaType.APPLICATION_JSON)
                                                    .header(HttpHeaders.AUTHORIZATION, testJWTToken)
                                                    .content(requestBody))
                                            .andExpect(status().isOk()).andReturn();

        String response = responseEntity.getResponse().getContentAsString();
        assertEquals(requestBody, response);
        verify(mockDeadlineRepository, times(1)).findById(any(Long.class));
        verify(mockDeadlineRepository, times(1)).save(any(Deadline.class));
    }

    @Test
    public void test_putBadUserID() throws Exception{
        testDeadline.setUserID("test");

        mockMvc.perform(put(PUT_MAPPING).with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, testJWTToken)
                        .content(mapper.writeValueAsString(testDeadline)))
                .andExpect(status().isBadRequest()).andReturn();

        verify(mockDeadlineRepository, never()).findById(any(Long.class));
        verify(mockDeadlineRepository, never()).save(any(Deadline.class));
    }

    @Test
    public void test_putDeadlineNotFound() throws Exception{
        when(mockDeadlineRepository.findById(testDeadline.getId())).thenReturn(Optional.empty());

        mockMvc.perform(put(PUT_MAPPING).with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, testJWTToken)
                .content(mapper.writeValueAsString(testDeadline)))
                .andExpect(status().isBadRequest()).andReturn();

        verify(mockDeadlineRepository, times(1)).findById(any(Long.class));
        verify(mockDeadlineRepository, never()).save(any(Deadline.class));
    }

    @Test
    public void test_putDeadlineDiffUserID() throws Exception{
        Deadline diffDeadline = new Deadline(1L, "test", new Timestamp(0), "description");

        when(mockDeadlineRepository.findById(testDeadline.getId())).thenReturn(Optional.of(diffDeadline));

        mockMvc.perform(put(PUT_MAPPING).with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, testJWTToken)
                .content(mapper.writeValueAsString(testDeadline)))
                .andExpect(status().isBadRequest()).andReturn();

        verify(mockDeadlineRepository, times(1)).findById(any(Long.class));
        verify(mockDeadlineRepository, never()).save(any(Deadline.class));
    }

}
