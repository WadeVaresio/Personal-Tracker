package com.varesio.wade.personaltracker.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.varesio.wade.personaltracker.entities.SavedNote;
import com.varesio.wade.personaltracker.repositories.SavedNoteRepository;
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

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = SavedNoteController.class)
public class SavedNoteControllerTests {
    private final String testJWTToken = "Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6IlI0a3ZHbHoteExVVlJ3djFIdF91aSJ9.eyJodHRwczovL3BlcnNvbmFsLXRyYWNrZXItYXBpLmNvbSI6eyJlbWFpbCI6IndhZGV2YXJlc2lvQGdtYWlsLmNvbSIsImdpdmVuX25hbWUiOiJXYWRlIn0sImlzcyI6Imh0dHBzOi8vZGV2LWZlamZ1ODAyLnVzLmF1dGgwLmNvbS8iLCJzdWIiOiJnb29nbGUtb2F1dGgyfDExNjQxODE1NTE5MjYzODE3NzQ2MiIsImF1ZCI6WyJodHRwczovL3BlcnNvbmFsLXRyYWNrZXItYXBpLmNvbSIsImh0dHBzOi8vZGV2LWZlamZ1ODAyLnVzLmF1dGgwLmNvbS91c2VyaW5mbyJdLCJpYXQiOjE2MjY2NDExMDYsImV4cCI6MTYyNjcyNzUwNiwiYXpwIjoiUUpSSlF4VTc2SW9rMndOUkhscmd1Nmd3VVpVb1FTU1ciLCJzY29wZSI6Im9wZW5pZCBwcm9maWxlIGVtYWlsIn0.MsJqi5LDPgC69dZrKbZwIuzzoAhQ8q7jI6I2b7h3Qi2j7fxvEu5614SKJnUuAn0YuISu3UpLhjql-ccjyVX9-kr1DJ0ZZ5LZguuYV8UnKFTu1W_vJIzZf7PQBAMr78NbQbmzdPx3uQZHjVgHTdP0roBaQFB60pVG88iyy3-lMt7tA87N26RsSIdwQwSgLSGOoiFEoWZuxsiuvv3l_IslUfmA4qQuHTE82TiFlCqFhuIxer-muXXo6E7xekyp4seeg-Uny4E2xLurRQ15HDPnAYgPOfKILNgUlgG8G3Dl9wrCp_RAbQ6V4eHN0-VhkTF--HpfHJcMCtlEvzW8mX7k5g";

    private final SavedNote savedNote1 = new SavedNote(1L, "wadevaresio@gmail.com", "test note");
    @MockBean
    SavedNoteRepository savedNoteRepository;

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private WebApplicationContext context;

    private final String GET_MAPPING = "/api/private/savedNotes/get";
    private final String DELETE_MAPPING = "/api/private/savedNotes/delete";
    private final String PUT_MAPPING = "/api/private/savedNotes/put";
    private final String POST_MAPPING = "/api/private/savedNotes/new";

    @BeforeEach
    public void setup(){
        mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
    }

    @Test
    public void test_newSavedNote() throws Exception {
        String requestBody = mapper.writeValueAsString(savedNote1);

        when(savedNoteRepository.save(any(SavedNote.class))).thenReturn(savedNote1);

        MvcResult responseEntity = mockMvc.perform(post(POST_MAPPING).with(csrf())
                                                    .contentType(MediaType.APPLICATION_JSON)
                                                    .header(HttpHeaders.AUTHORIZATION, testJWTToken)
                                                    .content(requestBody))
                                    .andExpect(status().isOk()).andReturn();

        String response = responseEntity.getResponse().getContentAsString();
        SavedNote responseNote = mapper.readValue(response, SavedNote.class);
        assertEquals(savedNote1, responseNote);
    }

    @Test
    public void test_newNoteBadUserID() throws Exception{
        savedNote1.setUserID("foo bar");
        String requestBody = mapper.writeValueAsString(savedNote1);

        mockMvc.perform(post(POST_MAPPING).with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, testJWTToken)
                        .content(requestBody))
                .andExpect(status().isBadRequest()).andReturn();

        verify(savedNoteRepository, never()).save(any(SavedNote.class));
    }

    @Test
    public void test_newNoteNoUserID() throws Exception{
        savedNote1.setUserID("");
        String requestBody = mapper.writeValueAsString(savedNote1);

        mockMvc.perform(post(POST_MAPPING).with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, testJWTToken)
                        .content(requestBody))
                .andExpect(status().isBadRequest()).andReturn();

        verify(savedNoteRepository, never()).save(any(SavedNote.class));
    }

    @Test
    public void test_newEmptyNoteNoSave() throws Exception{
        savedNote1.setNote("");
        String requestBody = mapper.writeValueAsString(savedNote1);

        mockMvc.perform(post(POST_MAPPING).with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, testJWTToken)
                .content(requestBody))
                .andExpect(status().isBadRequest()).andReturn();

        verify(savedNoteRepository, never()).save(any(SavedNote.class));
    }

    @Test
    public void test_editNote() throws Exception {
        when(savedNoteRepository.findById(savedNote1.getId())).thenReturn(Optional.of(savedNote1));

        savedNote1.setNote("Test Note"); // Edit the note
        String requestBody = mapper.writeValueAsString(savedNote1);

        MvcResult responseEntity = mockMvc
                .perform(put(PUT_MAPPING).with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, testJWTToken)
                        .content(requestBody))
                .andExpect(status().isOk()).andReturn();

        String response = responseEntity.getResponse().getContentAsString();
        SavedNote responseNote = mapper.readValue(response, SavedNote.class);
        assertEquals(savedNote1, responseNote);
        verify(savedNoteRepository, times(1)).findById(savedNote1.getId());
    }

    @Test
    public void test_editNoteBadUserID() throws Exception{
        savedNote1.setUserID("foo bar");
        String requestBody = mapper.writeValueAsString(savedNote1);

        mockMvc.perform(put(PUT_MAPPING).with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, testJWTToken)
                        .content(requestBody))
                .andExpect(status().isBadRequest()).andReturn();

        verify(savedNoteRepository, never()).findById(any(Long.class));
        verify(savedNoteRepository, never()).save(any(SavedNote.class));
    }

    @Test
    public void test_editNoteDiffSearchResult() throws Exception{
        String requestBody = mapper.writeValueAsString(savedNote1);

        savedNote1.setUserID("foo bar");
        when(savedNoteRepository.findById(any(Long.class))).thenReturn(Optional.of(savedNote1));

        mockMvc.perform(put(PUT_MAPPING).with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, testJWTToken)
                .content(requestBody))
                .andExpect(status().isBadRequest()).andReturn();

        verify(savedNoteRepository, never()).save(any(SavedNote.class));
        verify(savedNoteRepository,times(1)).findById(any(Long.class));
    }

    @Test
    public void test_editNoteNotFound() throws Exception {
        when(savedNoteRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        String requestBody = mapper.writeValueAsString(savedNote1);

        mockMvc.perform(put(PUT_MAPPING).with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, testJWTToken)
                        .content(requestBody))
                .andExpect(status().isNotFound()).andReturn();
        verify(savedNoteRepository, never()).save(any(SavedNote.class));
    }

    @Test
    public void test_deleteEmptyNote() throws Exception {
        when(savedNoteRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        String requestBody = mapper.writeValueAsString(savedNote1);

        mockMvc.perform(delete(DELETE_MAPPING).with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, testJWTToken)
                        .content(requestBody))
                .andExpect(status().isBadRequest()).andReturn();

        verify(savedNoteRepository, never()).delete(any(SavedNote.class));
        verify(savedNoteRepository, times(1)).findById(savedNote1.getId());
    }

    @Test
    public void test_deleteNoteNotEquals() throws Exception {
        when(savedNoteRepository.findById(savedNote1.getId())).thenReturn(Optional.of(new SavedNote()));

        String requestBody = mapper.writeValueAsString(savedNote1);
        mockMvc.perform(delete(DELETE_MAPPING).with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, testJWTToken)
                        .content(requestBody))
                .andExpect(status().isBadRequest()).andReturn();

        verify(savedNoteRepository, never()).delete(any(SavedNote.class));
        verify(savedNoteRepository, times(1)).findById(any(Long.class));
    }

    @Test
    public void test_delete() throws Exception{
        when(savedNoteRepository.findById(savedNote1.getId())).thenReturn(Optional.of(savedNote1));

        String requestBody = mapper.writeValueAsString(savedNote1);

        mockMvc.perform(delete(DELETE_MAPPING).with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, testJWTToken)
                        .content(requestBody))
                .andExpect(status().isNoContent()).andReturn();

        verify(savedNoteRepository, times(1)).findById(any(Long.class));
        verify(savedNoteRepository, times(1)).delete(any(SavedNote.class));
    }

    @Test
    public void test_deleteDiffUserID() throws Exception{
        savedNote1.setUserID("foo bar");
        String requestBody = mapper.writeValueAsString(savedNote1);

        mockMvc.perform(delete(DELETE_MAPPING).with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, testJWTToken)
                        .content(requestBody))
                .andExpect(status().isBadRequest()).andReturn();

        verify(savedNoteRepository, never()).findById(any(Long.class));
        verify(savedNoteRepository, never()).delete(any(SavedNote.class));
    }

    @Test
    public void test_deleteDiffSavedUserID() throws Exception{
        String requestBody = mapper.writeValueAsString(savedNote1);

        savedNote1.setUserID("foo bar");
        when(savedNoteRepository.findById(savedNote1.getId())).thenReturn(Optional.of(savedNote1));

        mockMvc.perform(delete(DELETE_MAPPING).with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, testJWTToken)
                        .content(requestBody))
                .andExpect(status().isBadRequest()).andReturn();

        verify(savedNoteRepository, times(1)).findById(any(Long.class));
        verify(savedNoteRepository, never()).delete(any(SavedNote.class));
    }

    @Test
    public void test_getAllNotes() throws Exception{
        when(savedNoteRepository.findAllByUserID(any(String.class))).thenReturn(List.of(savedNote1));

        String expectedResponse = mapper.writeValueAsString(List.of(savedNote1));

        MvcResult responseEntity = mockMvc.perform(get(GET_MAPPING).with(csrf())
                                                    .contentType(MediaType.APPLICATION_JSON)
                                                    .header(HttpHeaders.AUTHORIZATION, testJWTToken))
                                    .andExpect(status().isOk()).andReturn();
        String actualResponse = responseEntity.getResponse().getContentAsString();

        assertEquals(expectedResponse, actualResponse);
        verify(savedNoteRepository, times(1)).findAllByUserID(any(String.class));
    }

}
