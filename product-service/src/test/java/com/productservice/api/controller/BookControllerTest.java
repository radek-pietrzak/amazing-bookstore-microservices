package com.productservice.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.productservice.SpringdocConfig;
import com.productservice.TagGroup;
import com.productservice.api.service.BookService;
import com.productservice.api.service.ValidationService;
import com.productservice.validation.ValidationErrors;
import com.productservice.api.repository.BookRequestExamples;
import com.productservice.api.request.BookRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
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
    @Mock
    private ValidationService validationService;
    private ObjectMapper mapper;
    @Mock
    private SpringdocConfig springdocConfig;

    @BeforeEach
    public void setUp() throws IOException {
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        doNothing().when(bookService).saveBook(any());
        doCallRealMethod().when(validationService).errorMessages(any());
        when(springdocConfig.baseOpenAPI()).thenReturn(null);
    }

    @Test
    @Tag(TagGroup.ERRORS)
    void shouldReturnBadRequestIfBookAllNulls() throws Exception {
        //given
        BookRequest request = BookRequestExamples.BOOK_ALL_NULLS;
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
    @Tag(TagGroup.ERRORS)
    void shouldReturnBadRequestIfAuthorAllNulls() throws Exception {
        //given
        BookRequest request = BookRequestExamples.BOOK_AUTHOR_NULLS;
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
    @Tag(TagGroup.ERRORS)
    void shouldReturnBadRequestIfPublisherAllNulls() throws Exception {
        //given
        BookRequest request = BookRequestExamples.BOOK_PUBLISHER_NULLS;
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
    @Tag(TagGroup.ERRORS)
    void shouldReturnBadRequestIfInvalidISBN(BookRequest request) throws Exception {
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
                BookRequestExamples.INVALID_ISBN_1,
                BookRequestExamples.INVALID_ISBN_2,
                BookRequestExamples.INVALID_ISBN_3,
                BookRequestExamples.INVALID_ISBN_4
        );
    }

    @ParameterizedTest
    @MethodSource("invalidTitleSizeProvider")
    @Tag(TagGroup.ERRORS)
    void shouldReturnBadRequestIfInvalidTitleSize(BookRequest request) throws Exception {
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
                BookRequestExamples.INVALID_TITLE_SIZE_MIN,
                BookRequestExamples.INVALID_TITLE_SIZE_MAX
        );
    }

    @ParameterizedTest
    @MethodSource("invalidDescriptionSizeProvider")
    @Tag(TagGroup.ERRORS)
    void shouldReturnBadRequestIfInvalidDescriptionSize(BookRequest request) throws Exception {
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
                BookRequestExamples.INVALID_DESCRIPTION_SIZE_MIN,
                BookRequestExamples.INVALID_DESCRIPTION_SIZE_MAX
        );
    }

    @ParameterizedTest
    @MethodSource("invalidPublishDateFormatProvider")
    @Tag(TagGroup.ERRORS)
    void shouldReturnBadRequestIfInvalidPublishDateFormat(BookRequest request) throws Exception {
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
    @Tag(TagGroup.ERRORS)
    void shouldReturnBadRequestIfInvalidPageCountMin(BookRequest request) throws Exception {
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
    @Tag(TagGroup.ERRORS)
    void shouldReturnBadRequestIfInvalidPageCountMax(BookRequest request) throws Exception {
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
    @Tag(TagGroup.ERRORS)
    void shouldReturnBadRequestIfInvalidLanguageCode(BookRequest request) throws Exception {
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
    @Tag(TagGroup.ERRORS)
    void shouldReturnBadRequestIfInvalidAuthorNameLength(BookRequest request) throws Exception {
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

        assertTrue(result.contains(ValidationErrors.AUTHOR_NAME_LENGTH));

    }

    private static List<BookRequest> invalidAuthorNameProvider() {
        return List.of(
                BookRequestExamples.INVALID_AUTHOR_NAME_1,
                BookRequestExamples.INVALID_AUTHOR_NAME_2
        );
    }

    @ParameterizedTest
    @MethodSource("invalidAuthorDescriptionProvider")
    @Tag(TagGroup.ERRORS)
    void shouldReturnBadRequestIfInvalidAuthorDescriptionLength(BookRequest request) throws Exception {
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

        assertTrue(result.contains(ValidationErrors.AUTHOR_DESCRIPTION_LENGTH));

    }

    private static List<BookRequest> invalidAuthorDescriptionProvider() {
        return List.of(
                BookRequestExamples.INVALID_AUTHOR_DESCRIPTION_1,
                BookRequestExamples.INVALID_AUTHOR_DESCRIPTION_2
        );
    }

    @ParameterizedTest
    @MethodSource("invalidCategoryProvider")
    @Tag(TagGroup.ERRORS)
    void shouldReturnBadRequestIfInvalidCategory(BookRequest request) throws Exception {
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

        assertTrue(result.contains(ValidationErrors.CATEGORY_INVALID));

    }

    private static List<BookRequest> invalidCategoryProvider() {
        return List.of(
                BookRequestExamples.INVALID_CATEGORY_1,
                BookRequestExamples.INVALID_CATEGORY_2,
                BookRequestExamples.INVALID_CATEGORY_3,
                BookRequestExamples.INVALID_CATEGORY_4
        );
    }

    @ParameterizedTest
    @MethodSource("invalidPublisherNameProvider")
    @Tag(TagGroup.ERRORS)
    void shouldReturnBadRequestIfInvalidPublisherName(BookRequest request) throws Exception {
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

        assertTrue(result.contains(ValidationErrors.PUBLISHER_NAME_LENGTH));

    }

    private static List<BookRequest> invalidPublisherNameProvider() {
        return List.of(
                BookRequestExamples.INVALID_PUBLISHER_NAME_LENGTH_1,
                BookRequestExamples.INVALID_PUBLISHER_NAME_LENGTH_2
        );
    }

    @Test
    @Tag(TagGroup.ERRORS)
    void shouldReturnBadRequestIfInvalidPublisherName() throws Exception {
        //given
        BookRequest request = BookRequestExamples.INVALID_PUBLISHER_DESCRIPTION_LENGTH_1;
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

        assertTrue(result.contains(ValidationErrors.PUBLISHER_DESCRIPTION_LENGTH));

    }

    @ParameterizedTest
    @MethodSource("validBooksProvider")
    @Tag(TagGroup.SAVE_BOOK)
    void shouldAcceptedIfValidBook(BookRequest request) throws Exception {
        //given
        //when
        //then
        mockMvc.perform(post(API.BOOK_SAVE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isAccepted())
                .andReturn()
                .getResponse()
                .getContentAsString();

    }

    private static List<BookRequest> validBooksProvider() {
        return List.of(
                BookRequestExamples.VALID_BOOK_1,
                BookRequestExamples.VALID_BOOK_2
        );
    }

}
