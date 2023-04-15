package com.productservice.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.productservice.api.repository.RequestBookExamples;
import com.productservice.api.request.BookRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class BookControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private BookController bookController;

    @BeforeEach
    public void sutUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
    }

    @Test
    void saveBook_shouldValidateBookIfNulls() throws Exception {
        //given
        BookRequest request = RequestBookExamples.BOOK_ALL_NULLS;

        //when
        //then
        String result = mockMvc.perform(post(API.BOOK_SAVE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertTrue(result.contains("ISBN cannot be null"));
        assertTrue(result.contains("Title cannot be null"));
        assertTrue(result.contains("Description cannot be null"));
        assertTrue(result.contains("Publish Date cannot be null"));
        assertTrue(result.contains("Page Count cannot be null"));
        assertTrue(result.contains("Language cannot be null"));
        assertTrue(result.contains("Authors cannot be null"));
        assertTrue(result.contains("Categories cannot be null"));
        assertTrue(result.contains("Publisher cannot be null"));

    }

    @Test
    void saveBook_shouldValidateAuthorIfNulls() throws Exception {
        //given
        BookRequest request = RequestBookExamples.BOOK_AUTHOR_NULLS;

        //when
        //then
        String result = mockMvc.perform(post(API.BOOK_SAVE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertTrue(result.contains("Author Name cannot be null"));

    }

    @Test
    void saveBook_shouldValidatePublisherIfNulls() throws Exception {
        //given
        BookRequest request = RequestBookExamples.BOOK_PUBLISHER_NULLS;

        //when
        //then
        String result = mockMvc.perform(post(API.BOOK_SAVE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertTrue(result.contains("Publisher Name cannot be null"));

    }
}
