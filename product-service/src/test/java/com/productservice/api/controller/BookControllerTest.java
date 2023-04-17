package com.productservice.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.productservice.ValidationErrors;
import com.productservice.api.repository.RequestBookExamples;
import com.productservice.api.request.BookRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class BookControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private BookController bookController;

    private ObjectMapper mapper;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
    }

    @Test
    void saveBook_shouldValidateBookIfNulls() throws Exception {
        //given
        BookRequest request = RequestBookExamples.BOOK_ALL_NULLS;

        //when
        //then
        String result = mockMvc.perform(post(API.BOOK_SAVE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertTrue(result.contains(ValidationErrors.ISBN_NULL));
        assertTrue(result.contains(ValidationErrors.TITLE_NULL));
        assertTrue(result.contains(ValidationErrors.DESCRIPTION_NULL));
        assertTrue(result.contains(ValidationErrors.PUBLISH_DATE_NULL));
        assertTrue(result.contains(ValidationErrors.PAGE_COUNT_NULL));
        assertTrue(result.contains(ValidationErrors.LANGUAGE_NULL));
        assertTrue(result.contains(ValidationErrors.AUTHORS_NULL));
        assertTrue(result.contains(ValidationErrors.CATEGORIES_NULL));
        assertTrue(result.contains(ValidationErrors.PUBLISHER_NULL));

    }

    @Test
    void saveBook_shouldValidateAuthorIfNulls() throws Exception {
        //given
        BookRequest request = RequestBookExamples.BOOK_AUTHOR_NULLS;

        //when
        //then
        String result = mockMvc.perform(post(API.BOOK_SAVE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertTrue(result.contains(ValidationErrors.AUTHOR_NAME_NULL));

    }

    @Test
    void saveBook_shouldValidatePublisherIfNulls() throws Exception {
        //given
        BookRequest request = RequestBookExamples.BOOK_PUBLISHER_NULLS;

        //when
        //then
        String result = mockMvc.perform(post(API.BOOK_SAVE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertTrue(result.contains(ValidationErrors.PUBLISHER_NAME_NULL));

    }

    @ParameterizedTest
    @MethodSource("invalidISBNProvider")
    void saveBook_shouldValidateISBN(BookRequest request) throws Exception {
        //given
        //when
        //then
        String result = mockMvc.perform(post(API.BOOK_SAVE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertTrue(result.contains(ValidationErrors.ISBN_INVALID));

    }

    private static List<BookRequest> invalidISBNProvider() {
        return List.of(
                RequestBookExamples.INVALID_ISBN_1,
                RequestBookExamples.INVALID_ISBN_2,
                RequestBookExamples.INVALID_ISBN_3,
                RequestBookExamples.INVALID_ISBN_4
        );
    }

    @ParameterizedTest
    @MethodSource("invalidTitleSizeProvider")
    void saveBook_shouldValidateTitleSize(BookRequest request) throws Exception {
        //given
        //when
        //then
        String result = mockMvc.perform(post(API.BOOK_SAVE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertTrue(result.contains(ValidationErrors.TITLE_LENGTH));

    }

    private static List<BookRequest> invalidTitleSizeProvider() {
        return List.of(
                RequestBookExamples.INVALID_TITLE_SIZE_MIN,
                RequestBookExamples.INVALID_TITLE_SIZE_MAX
        );
    }

    @ParameterizedTest
    @MethodSource("invalidDescriptionSizeProvider")
    void saveBook_shouldValidateDescriptionSize(BookRequest request) throws Exception {
        //given
        //when
        //then
        String result = mockMvc.perform(post(API.BOOK_SAVE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertTrue(result.contains(ValidationErrors.DESCRIPTION_LENGTH));

    }

    private static List<BookRequest> invalidDescriptionSizeProvider() {
        return List.of(
                RequestBookExamples.INVALID_DESCRIPTION_SIZE_MIN,
                RequestBookExamples.INVALID_DESCRIPTION_SIZE_MAX
        );
    }

    @ParameterizedTest
    @MethodSource("invalidPublishDateFormatProvider")
    void saveBook_shouldValidatePublishDateFormat(BookRequest request) throws Exception {
        //given
        //when
        //then
        String result = mockMvc.perform(post(API.BOOK_SAVE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertTrue(result.contains(ValidationErrors.PUBLISH_DATE_FORMAT));

    }

    private static List<BookRequest> invalidPublishDateFormatProvider() {
        return List.of(
                RequestBookExamples.INVALID_LOCAL_DATE_FORMAT_1,
                RequestBookExamples.INVALID_LOCAL_DATE_FORMAT_2,
                RequestBookExamples.INVALID_LOCAL_DATE_FORMAT_3,
                RequestBookExamples.INVALID_LOCAL_DATE_FORMAT_4,
                RequestBookExamples.INVALID_LOCAL_DATE_FORMAT_5,
                RequestBookExamples.INVALID_LOCAL_DATE_FORMAT_6
        );
    }

}
