package com.varesio.wade.personaltracker.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.varesio.wade.personaltracker.entities.SavedNote;
import com.varesio.wade.personaltracker.repositories.SavedNoteRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = SavedNoteController.class)
public class SavedNoteControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    SavedNoteRepository savedNoteRepository;

    private final SavedNote savedNote1 = new SavedNote(1L, "abc", "test note");

    @Test
    public void test_newSavedNote() throws Exception {
        String requestBody = mapper.writeValueAsString(savedNote1);

        when(savedNoteRepository.save(any(SavedNote.class))).thenReturn(savedNote1);

        MvcResult responseEntity = mockMvc
                .perform(post("/api/savedNotes/new").with(csrf()).contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8").content(requestBody))
                .andExpect(status().isOk()).andReturn();

        String response = responseEntity.getResponse().getContentAsString();
        SavedNote responseNote = mapper.readValue(response, SavedNote.class);
        assertEquals(savedNote1, responseNote);
    }

    @Test
    public void test_editNote() throws Exception {
        when(savedNoteRepository.findById(savedNote1.getId())).thenReturn(Optional.of(savedNote1));

        savedNote1.setNote("Test Note"); // Edit the note
        String requestBody = mapper.writeValueAsString(savedNote1);

        MvcResult responseEntity = mockMvc
                .perform(put("/api/savedNotes/put").with(csrf()).contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8").content(requestBody))
                .andExpect(status().isOk()).andReturn();

        String response = responseEntity.getResponse().getContentAsString();
        SavedNote responseNote = mapper.readValue(response, SavedNote.class);
        assertEquals(savedNote1, responseNote);
        verify(savedNoteRepository, times(1)).findById(savedNote1.getId());
    }
}
