package com.productservice.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.productservice.api.service.BookService;
import com.productservice.validation.ValidationErrors;
import com.productservice.api.repository.BookRequestExamples;
import com.productservice.api.request.BookRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class BookControllerTest {

    private MockMvc mockMvc;
    @InjectMocks
    private BookController bookController;
    @Mock
    private BookService bookService;
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
        BookRequest request = BookRequestExamples.BOOK_ALL_NULLS;
        doNothing().when(bookService).saveBook(request);
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
        BookRequest request = BookRequestExamples.BOOK_AUTHOR_NULLS;
        doNothing().when(bookService).saveBook(request);
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
        BookRequest request = BookRequestExamples.BOOK_PUBLISHER_NULLS;
        doNothing().when(bookService).saveBook(request);
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
        doNothing().when(bookService).saveBook(request);
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
                BookRequestExamples.INVALID_ISBN_1,
                BookRequestExamples.INVALID_ISBN_2,
                BookRequestExamples.INVALID_ISBN_3,
                BookRequestExamples.INVALID_ISBN_4
        );
    }

    @ParameterizedTest
    @MethodSource("invalidTitleSizeProvider")
    void saveBook_shouldValidateTitleSize(BookRequest request) throws Exception {
        //given
        doNothing().when(bookService).saveBook(request);
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
                BookRequestExamples.INVALID_TITLE_SIZE_MIN,
                BookRequestExamples.INVALID_TITLE_SIZE_MAX
        );
    }

    @ParameterizedTest
    @MethodSource("invalidDescriptionSizeProvider")
    void saveBook_shouldValidateDescriptionSize(BookRequest request) throws Exception {
        //given
        doNothing().when(bookService).saveBook(request);
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
                BookRequestExamples.INVALID_DESCRIPTION_SIZE_MIN,
                BookRequestExamples.INVALID_DESCRIPTION_SIZE_MAX
        );
    }

    @ParameterizedTest
    @MethodSource("invalidPublishDateFormatProvider")
    void saveBook_shouldValidatePublishDateFormat(BookRequest request) throws Exception {
        //given
        doNothing().when(bookService).saveBook(request);
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
                BookRequestExamples.INVALID_LOCAL_DATE_FORMAT_1,
                BookRequestExamples.INVALID_LOCAL_DATE_FORMAT_2,
                BookRequestExamples.INVALID_LOCAL_DATE_FORMAT_3,
                BookRequestExamples.INVALID_LOCAL_DATE_FORMAT_4,
                BookRequestExamples.INVALID_LOCAL_DATE_FORMAT_5,
                BookRequestExamples.INVALID_LOCAL_DATE_FORMAT_6
        );
    }

    @ParameterizedTest
    @MethodSource("invalidPageCountMinProvider")
    void saveBook_shouldValidatePageCountMin(BookRequest request) throws Exception {
        //given
        doNothing().when(bookService).saveBook(request);
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

        assertTrue(result.contains(ValidationErrors.PAGE_COUNT_MIN));

    }

    private static List<BookRequest> invalidPageCountMinProvider() {
        return List.of(
                BookRequestExamples.INVALID_PAGE_COUNT_MIN_1,
                BookRequestExamples.INVALID_PAGE_COUNT_MIN_2
        );
    }

    @ParameterizedTest
    @MethodSource("invalidPageCountMaxProvider")
    void saveBook_shouldValidatePageCountMax(BookRequest request) throws Exception {
        //given
        doNothing().when(bookService).saveBook(request);
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

        assertTrue(result.contains(ValidationErrors.PAGE_COUNT_MAX));

    }

    private static List<BookRequest> invalidPageCountMaxProvider() {
        return List.of(
                BookRequestExamples.INVALID_PAGE_COUNT_MAX_1,
                BookRequestExamples.INVALID_PAGE_COUNT_MAX_2
        );
    }

    @ParameterizedTest
    @MethodSource("invalidLanguageCodeProvider")
    void saveBook_shouldValidateLanguageCode(BookRequest request) throws Exception {
        //given
        doNothing().when(bookService).saveBook(request);
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

        assertTrue(result.contains(ValidationErrors.LANG_CODE));

    }

    private static List<BookRequest> invalidLanguageCodeProvider() {
        return List.of(
                BookRequestExamples.INVALID_LANG_CODE_1,
                BookRequestExamples.INVALID_LANG_CODE_2,
                BookRequestExamples.INVALID_LANG_CODE_3
        );
    }

    @ParameterizedTest
    @MethodSource("invalidAuthorNameProvider")
    void saveBook_shouldValidateAuthorNameLength(BookRequest request) throws Exception {
        //given
        doNothing().when(bookService).saveBook(request);
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

        assertTrue(result.contains(ValidationErrors.AUTHOR_NAME_LENGTH));

    }

    private static List<BookRequest> invalidAuthorNameProvider() {
        return List.of(
                BookRequestExamples.INVALID_AUTHOR_NAME_1,
                BookRequestExamples.INVALID_AUTHOR_NAME_2
        );
    }

}
