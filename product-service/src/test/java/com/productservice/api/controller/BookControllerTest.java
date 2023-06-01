package com.productservice.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.productservice.TagGroup;
import com.productservice.api.example.BookRequestExamplesTest;
import com.productservice.api.example.BookResponseExamplesTest;
import com.productservice.api.service.BookService;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.ArrayList;
import java.util.List;

import static com.productservice.api.example.BookRequestExamplesTest.*;
import static com.productservice.api.request.JsonPropertyNames.*;
import static com.productservice.validation.ValidationErrors.*;
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
    private ObjectMapper mapper;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
    }

    @ParameterizedTest
    @MethodSource("requestProvider")
    @Tag(TagGroup.VALIDATION)
    void shouldReturnBadRequest(RequestProvider requestProvider) throws Exception {
        //given
        BookRequest request = requestProvider.request;
        String field = requestProvider.field;
        String error = requestProvider.error;
        //when
        //then
        Exception result = mockMvc.perform(post(API.BOOK_SAVE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResolvedException();

        assertNotNull(result);
        assertTrue(result instanceof MethodArgumentNotValidException);
        MethodArgumentNotValidException validationException = (MethodArgumentNotValidException) result;
        BindingResult bindingResult = validationException.getBindingResult();
        assertNotNull(bindingResult.getFieldError(field));
        assertTrue(result.getMessage().contains(error));
    }

    private record RequestProvider(String field, BookRequest request, String error) {
    }

    private static List<RequestProvider> requestProvider() {
        return List.of(
                new RequestProvider(ISBN, BOOK_ALL_NULLS, ISBN_NULL),
                new RequestProvider(ISBN, INVALID_ISBN_1, ISBN_INVALID),
                new RequestProvider(ISBN, INVALID_ISBN_2, ISBN_INVALID),
                new RequestProvider(ISBN, INVALID_ISBN_3, ISBN_INVALID),
                new RequestProvider(ISBN, INVALID_ISBN_4, ISBN_INVALID),
                new RequestProvider(TITLE, BOOK_ALL_NULLS, TITLE_NULL),
                new RequestProvider(TITLE, INVALID_TITLE_SIZE_MIN, TITLE_LENGTH),
                new RequestProvider(TITLE, INVALID_TITLE_SIZE_MAX, TITLE_LENGTH),
                new RequestProvider(DESCRIPTION, BOOK_ALL_NULLS, DESCRIPTION_NULL),
                new RequestProvider(DESCRIPTION, INVALID_DESCRIPTION_SIZE_MIN, DESCRIPTION_LENGTH),
                new RequestProvider(DESCRIPTION, INVALID_DESCRIPTION_SIZE_MAX, DESCRIPTION_LENGTH),
                new RequestProvider(PUBLISH_YEAR, BOOK_ALL_NULLS, PUBLISH_YEAR_NULL),
                new RequestProvider(PAGE_COUNT, BOOK_ALL_NULLS, PAGE_COUNT_NULL),
                new RequestProvider(PAGE_COUNT, INVALID_PAGE_COUNT_MIN_1, PAGE_COUNT_MIN),
                new RequestProvider(PAGE_COUNT, INVALID_PAGE_COUNT_MIN_2, PAGE_COUNT_MIN),
                new RequestProvider(PAGE_COUNT, INVALID_PAGE_COUNT_MAX_1, PAGE_COUNT_MAX),
                new RequestProvider(PAGE_COUNT, INVALID_PAGE_COUNT_MAX_2, PAGE_COUNT_MAX),
                new RequestProvider(LANG_CODE, BOOK_ALL_NULLS, LANGUAGE_NULL),
                new RequestProvider(LANG_CODE, INVALID_LANG_CODE_1, LANG_CODE_INVALID),
                new RequestProvider(LANG_CODE, INVALID_LANG_CODE_2, LANG_CODE_INVALID),
                new RequestProvider(LANG_CODE, INVALID_LANG_CODE_3, LANG_CODE_INVALID),
                new RequestProvider(AUTHORS, BOOK_ALL_NULLS, AUTHORS_NULL),
                new RequestProvider("authors[0]." + AUTHOR_NAME, BOOK_AUTHOR_NULLS, AUTHOR_NAME_NULL),
                new RequestProvider("authors[0]." + AUTHOR_NAME, INVALID_AUTHOR_NAME_1, AUTHOR_NAME_LENGTH),
                new RequestProvider("authors[0]." + AUTHOR_NAME, INVALID_AUTHOR_NAME_2, AUTHOR_NAME_LENGTH),
                new RequestProvider("authors[0]." + AUTHOR_DESCRIPTION, INVALID_AUTHOR_DESCRIPTION_1, AUTHOR_DESCRIPTION_LENGTH),
                new RequestProvider("authors[0]." + AUTHOR_DESCRIPTION, INVALID_AUTHOR_DESCRIPTION_2, AUTHOR_DESCRIPTION_LENGTH),
                new RequestProvider(CATEGORIES, BOOK_ALL_NULLS, CATEGORIES_NULL),
                new RequestProvider(CATEGORIES, INVALID_CATEGORY_1, CATEGORY_INVALID),
                new RequestProvider(CATEGORIES, INVALID_CATEGORY_2, CATEGORY_INVALID),
                new RequestProvider(CATEGORIES, INVALID_CATEGORY_3, CATEGORY_INVALID),
                new RequestProvider(CATEGORIES, INVALID_CATEGORY_4, CATEGORY_INVALID),
                new RequestProvider(PUBLISHER, BOOK_ALL_NULLS, PUBLISHER_NULL),
                new RequestProvider("publisher." + PUBLISHER_NAME, BOOK_PUBLISHER_NULLS, PUBLISHER_NAME_NULL),
                new RequestProvider("publisher." + PUBLISHER_NAME, INVALID_PUBLISHER_NAME_LENGTH_1, PUBLISHER_NAME_LENGTH),
                new RequestProvider("publisher." + PUBLISHER_NAME, INVALID_PUBLISHER_NAME_LENGTH_2, PUBLISHER_NAME_LENGTH),
                new RequestProvider("publisher." + PUBLISHER_DESCRIPTION, INVALID_PUBLISHER_DESCRIPTION_LENGTH, PUBLISHER_DESCRIPTION_LENGTH)
        );
    }

    @Test
    @Tag(TagGroup.VALIDATION)
    @Tag(TagGroup.EDIT_BOOK)
    void shouldReturnModifiedBook() throws Exception {
        //given
        BookRequest request = BookRequestExamplesTest.VALID_BOOK_1;
        String id = "1";
        when(bookService.editBook(id, request)).thenReturn(BookResponseExamplesTest.VALID_BOOK_1);
        //when
        //then
        mockMvc.perform(post(API.BOOK_EDIT_ID, id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Tag(TagGroup.VALIDATION)
    @Tag(TagGroup.DELETE_BOOK)
    void shouldReturnOKIfValidBookId_deleteBook() throws Exception {
        //given
        String bookId = "1";
        when(bookService.deleteBook(any())).thenReturn(BookResponseExamplesTest.VALID_BOOK_1);
        //when
        //then
        mockMvc.perform(put(API.BOOK_DELETE_ID, bookId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Tag(TagGroup.VALIDATION)
    @Tag(TagGroup.GET_BOOK)
    void shouldReturnOKIfValidBookId() throws Exception {
        //given
        String bookId = "1";
        when(bookService.getBook(any())).thenReturn(BookResponseExamplesTest.VALID_BOOK_1);
        //when
        //then
        mockMvc.perform(get(API.BOOK_GET_ID, bookId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @ParameterizedTest
    @MethodSource("validBooksRequestProvider")
    @Tag(TagGroup.VALIDATION)
    @Tag(TagGroup.SAVE_BOOK)
    void shouldAcceptIfValidBook(BookRequest request) throws Exception {
        //given
        when(bookService.saveBook(request)).thenReturn(BookResponseExamplesTest.VALID_BOOK_1);
        //when
        //then
        mockMvc.perform(post(API.BOOK_SAVE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk());

    }

    private static List<BookRequest> validBooksRequestProvider() {
        return List.of(
                BookRequestExamplesTest.VALID_BOOK_1,
                BookRequestExamplesTest.VALID_BOOK_2
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
                .andExpect(status().isOk());
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
