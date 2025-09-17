package pl.radek.productservice.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import pl.radek.productservice.TagGroup;
import pl.radek.productservice.api.service.BookService;
import pl.radek.productservice.api.request.BookRequest;
import pl.radek.productservice.example.BookRequestExample;
import pl.radek.productservice.example.BookResponseExample;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pl.radek.productservice.api.request.JsonPropertyNames.*;
import static pl.radek.productservice.validation.ValidationErrors.*;

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
                new RequestProvider(ISBN, BookRequestExample.getBookAllNulls(), ISBN_NULL),
                new RequestProvider(ISBN, BookRequestExample.getInvalidISBN1(), ISBN_INVALID),
                new RequestProvider(ISBN, BookRequestExample.getInvalidISBN2(), ISBN_INVALID),
                new RequestProvider(ISBN, BookRequestExample.getInvalidISBN3(), ISBN_INVALID),
                new RequestProvider(ISBN, BookRequestExample.getInvalidISBN4(), ISBN_INVALID),
                new RequestProvider(TITLE, BookRequestExample.getBookAllNulls(), TITLE_NULL),
                new RequestProvider(TITLE, BookRequestExample.getInvalidTitleSizeMin(), TITLE_LENGTH),
                new RequestProvider(TITLE, BookRequestExample.getInvalidTitleSizeMax(), TITLE_LENGTH),
                new RequestProvider(DESCRIPTION, BookRequestExample.getBookAllNulls(), DESCRIPTION_NULL),
                new RequestProvider(DESCRIPTION, BookRequestExample.getInvalidDescriptionSizeMin(), DESCRIPTION_LENGTH),
                new RequestProvider(DESCRIPTION, BookRequestExample.getInvalidDescriptionSizeMax(), DESCRIPTION_LENGTH),
                new RequestProvider(PUBLISH_YEAR, BookRequestExample.getBookAllNulls(), PUBLISH_YEAR_NULL),
                new RequestProvider(PAGE_COUNT, BookRequestExample.getBookAllNulls(), PAGE_COUNT_NULL),
                new RequestProvider(PAGE_COUNT, BookRequestExample.getInvalidPageCountMin1(), PAGE_COUNT_MIN),
                new RequestProvider(PAGE_COUNT, BookRequestExample.getInvalidPageCountMin2(), PAGE_COUNT_MIN),
                new RequestProvider(PAGE_COUNT, BookRequestExample.getInvalidPageCountMax1(), PAGE_COUNT_MAX),
                new RequestProvider(PAGE_COUNT, BookRequestExample.getInvalidPageCountMax2(), PAGE_COUNT_MAX),
                new RequestProvider(LANG_CODE, BookRequestExample.getBookAllNulls(), LANGUAGE_NULL),
                new RequestProvider(LANG_CODE, BookRequestExample.getInvalidLangCode1(), LANG_CODE_INVALID),
                new RequestProvider(LANG_CODE, BookRequestExample.getInvalidLangCode2(), LANG_CODE_INVALID),
                new RequestProvider(LANG_CODE, BookRequestExample.getInvalidLangCode3(), LANG_CODE_INVALID),
                new RequestProvider(AUTHORS, BookRequestExample.getBookAllNulls(), AUTHORS_NULL),
                new RequestProvider("authors[0]." + AUTHOR_NAME, BookRequestExample.getBookAuthorNulls(), AUTHOR_NAME_NULL),
                new RequestProvider("authors[0]." + AUTHOR_NAME, BookRequestExample.getInvalidAuthorName1(), AUTHOR_NAME_LENGTH),
                new RequestProvider("authors[0]." + AUTHOR_NAME, BookRequestExample.getInvalidAuthorName2(), AUTHOR_NAME_LENGTH),
                new RequestProvider("authors[0]." + AUTHOR_DESCRIPTION, BookRequestExample.getInvalidAuthorDescription1(), AUTHOR_DESCRIPTION_LENGTH),
                new RequestProvider("authors[0]." + AUTHOR_DESCRIPTION, BookRequestExample.getInvalidAuthorDescription2(), AUTHOR_DESCRIPTION_LENGTH),
                new RequestProvider(CATEGORIES, BookRequestExample.getBookAllNulls(), CATEGORIES_NULL),
                new RequestProvider(CATEGORIES, BookRequestExample.getInvalidCategory1(), CATEGORY_INVALID),
                new RequestProvider(CATEGORIES, BookRequestExample.getInvalidCategory2(), CATEGORY_INVALID),
                new RequestProvider(CATEGORIES, BookRequestExample.getInvalidCategory3(), CATEGORY_INVALID),
                new RequestProvider(CATEGORIES, BookRequestExample.getInvalidCategory4(), CATEGORY_INVALID),
                new RequestProvider(PUBLISHER, BookRequestExample.getBookAllNulls(), PUBLISHER_NULL),
                new RequestProvider("publisher." + PUBLISHER_NAME, BookRequestExample.getBookPublisherNulls(), PUBLISHER_NAME_NULL),
                new RequestProvider("publisher." + PUBLISHER_NAME, BookRequestExample.getInvalidPublisherNameLength1(), PUBLISHER_NAME_LENGTH),
                new RequestProvider("publisher." + PUBLISHER_NAME, BookRequestExample.getInvalidPublisherNameLength2(), PUBLISHER_NAME_LENGTH),
                new RequestProvider("publisher." + PUBLISHER_DESCRIPTION, BookRequestExample.getInvalidPublisherDescriptionLength1(), PUBLISHER_DESCRIPTION_LENGTH)
        );
    }

    @Test
    @Tag(TagGroup.VALIDATION)
    @Tag(TagGroup.EDIT_BOOK)
    void shouldReturnModifiedBook() throws Exception {
        //given
        BookRequest request = BookRequestExample.getValidBook1();
        String id = "1";
        when(bookService.editBook(id, request)).thenReturn(BookResponseExample.getValidBook1());
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
        when(bookService.deleteBook(any())).thenReturn(BookResponseExample.getValidBook1());
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
        when(bookService.getBook(any())).thenReturn(BookResponseExample.getValidBook1());
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
        when(bookService.saveBook(request)).thenReturn(BookResponseExample.getValidBook1());
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
                BookRequestExample.getValidBook1(),
                BookRequestExample.getValidBook2()
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
