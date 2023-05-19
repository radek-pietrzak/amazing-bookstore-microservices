package com.productservice.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.productservice.TagGroup;
import com.productservice.api.examples.BookRequestExamples;
import com.productservice.api.examples.BookResponseExamples;
import com.productservice.api.response.BadRequestResponse;
import com.productservice.api.service.BookService;
import com.productservice.api.service.ValidationService;
import com.productservice.validation.ValidationErrors;
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

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        doNothing().when(bookService).saveBook(any());
        doCallRealMethod().when(validationService).errorMessages(any());
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
        assertTrue(result.contains(ValidationErrors.PUBLISH_YEAR_NULL));
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

    @Test
    @Tag(TagGroup.EDIT_BOOK)
    void shouldReturnModifiedBook() throws Exception {
        //given
        BookRequest request = BookRequestExamples.VALID_BOOK_1;
        String id = "1";
        when(bookService.editBook(any(), any())).thenReturn(BookResponseExamples.VALID_BOOK_1);
        //when
        //then
        mockMvc.perform(post(API.BOOK_EDIT, id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Tag(TagGroup.DELETE_BOOK)
    void shouldReturnBadRequestInvalidBookId_deleteBook() throws Exception {
        //given
        String bookId = "1";
        when(bookService.deleteBook(any())).thenReturn(null);
        //when
        //then
        String actual = mockMvc.perform(put(API.BOOK_DELETE, bookId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertTrue(actual.contains(BadRequestResponse.UNABLE_TO_FIND_BOOK));
    }

    @Test
    @Tag(TagGroup.DELETE_BOOK)
    void shouldReturnOKIfValidBookId_deleteBook() throws Exception {
        //given
        String bookId = "1";
        when(bookService.deleteBook(any())).thenReturn(BookResponseExamples.VALID_BOOK_1);
        //when
        //then
        mockMvc.perform(put(API.BOOK_DELETE, bookId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    @Tag(TagGroup.GET_BOOK)
    void shouldReturnOKIfValidBookId() throws Exception {
        //given
        String bookId = "1";
        when(bookService.getBook(any())).thenReturn(BookResponseExamples.VALID_BOOK_1);
        //when
        //then
        mockMvc.perform(get(API.GET_BOOK, bookId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @ParameterizedTest
    @MethodSource("validBooksRequestProvider")
    @Tag(TagGroup.SAVE_BOOK)
    void shouldAcceptIfValidBook(BookRequest request) throws Exception {
        //given
        //when
        //then
        mockMvc.perform(post(API.BOOK_SAVE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

    }

    private static List<BookRequest> validBooksRequestProvider() {
        return List.of(
                BookRequestExamples.VALID_BOOK_1,
                BookRequestExamples.VALID_BOOK_2
        );
    }

    @ParameterizedTest
    @MethodSource("anySearchProvider")
    @Tag(TagGroup.GET_BOOK_LIST)
    void shouldReturnOkResponseIfAnySearch(String search) throws Exception {
        //given
        //when
        //then
        mockMvc.perform(get(API.BOOK_LIST)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(search)))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

    }

    private static List<String> anySearchProvider() {
        List<String> list = new ArrayList<>();
        list.add(null);
        list.add("a");
        list.add("1");
        list.add("dklnnlkl");
        return list;
    }

}
