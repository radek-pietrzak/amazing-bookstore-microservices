package com.productservice.api.controller;

import com.productservice.TagGroup;
import com.productservice.api.example.BookRequestExamplesTest;
import com.productservice.api.example.BookResponseExamplesTest;
import com.productservice.api.response.EditBookResponse;
import com.productservice.api.response.Response;
import com.productservice.api.service.BookService;
import com.productservice.document.Book;
import com.productservice.api.example.BookExamplesTest;
import com.productservice.api.example.EditBookResponseExamplesTest;
import com.productservice.mapper.BookMapper;
import com.productservice.repository.BookRepository;
import com.productservice.api.request.BookRequest;
import com.productservice.api.response.BookResponse;
import com.productservice.api.response.BookResponseList;
import com.productservice.repository.BookRepositoryTemplate;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import jakarta.validation.Validator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @InjectMocks
    private BookService bookService;
    @Mock
    private BookRepository bookRepository;
    @Mock
    private BookRepositoryTemplate bookRepositoryTemplate;
    @Captor
    private ArgumentCaptor<Book> bookArgumentCaptor;
    @Mock
    private Validator validator;
    private final BookMapper bookMapper = Mappers.getMapper(BookMapper.class);

    @BeforeEach
    public void setUp() {
        bookService = new BookService(bookRepository, bookRepositoryTemplate, validator, bookMapper);
    }

    @ParameterizedTest
    @MethodSource("validBookPairResponseProvider")
    @Tag(TagGroup.GET_BOOK)
    void shouldReturnCorrectResponse(BookPairResponse bookPairResponse) {
        //given
        Response expected = bookPairResponse.bookResponse;
        when(bookRepository.findById(any())).thenReturn(Optional.ofNullable(bookPairResponse.book));
        //when
        Response actual = bookService.getBook("1");
        //then
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    private static List<BookPairResponse> validBookPairResponseProvider() {
        return List.of(
                new BookPairResponse(BookResponseExamplesTest.VALID_BOOK_1, BookExamplesTest.VALID_BOOK_1),
                new BookPairResponse(BookResponseExamplesTest.VALID_BOOK_2, BookExamplesTest.VALID_BOOK_2)
        );
    }

    private record BookPairResponse(BookResponse bookResponse, Book book) {
    }

    @ParameterizedTest
    @MethodSource("validBookPairRequestsProvider")
    @Tag(TagGroup.SAVE_BOOK)
    void shouldBuildCorrectBookToSave(BookPairRequest bookPairRequest) {
        //given
        BookRequest request = bookPairRequest.bookRequest;
        Book expected = bookPairRequest.book;

        //when
        bookService.saveBook(request);

        //then
        verify(bookRepository).save(bookArgumentCaptor.capture());
        Book actual = bookArgumentCaptor.getValue();

        assertNotNull(actual);
        assertNotNull(expected.getCreatedDate());
        assertInstanceOf(LocalDateTime.class, actual.getCreatedDate());
        assertEquals(expected.getIsbn(), actual.getIsbn());
        assertEquals(expected.getTitle(), actual.getTitle());
        assertEquals(expected.getDescription(), actual.getDescription());
        assertEquals(expected.getPublishYear(), actual.getPublishYear());
        assertEquals(expected.getPageCount(), actual.getPageCount());
        assertEquals(expected.getLanguageCode(), actual.getLanguageCode());
        assertNotNull(actual.getAuthors());
        assertEquals(expected.getAuthors().size(), actual.getAuthors().size());
        assertEquals(expected.getAuthors(), actual.getAuthors());
        assertEquals(expected.getCategories(), actual.getCategories());
        assertEquals(expected.getPublisher(), actual.getPublisher());
    }

    private static List<BookPairRequest> validBookPairRequestsProvider() {
        return List.of(
                new BookPairRequest(BookRequestExamplesTest.VALID_BOOK_1, BookExamplesTest.VALID_BOOK_1),
                new BookPairRequest(BookRequestExamplesTest.VALID_BOOK_2, BookExamplesTest.VALID_BOOK_2)
        );
    }

    private record BookPairRequest(BookRequest bookRequest, Book book) {
    }

    @Test
    @Tag(TagGroup.EDIT_BOOK)
    void shouldReturnModifiedBook() throws IllegalAccessException {
        //given
        Book bookToEdit = BookExamplesTest.copy(BookExamplesTest.VALID_BOOK_1);
        EditBookResponse expected = EditBookResponseExamplesTest.getEditBookResponse(true, BookResponseExamplesTest.VALID_BOOK_3);
        when(bookRepository.findById(any())).thenReturn(Optional.of(bookToEdit));
        BookRequest bookRequest = BookRequestExamplesTest.VALID_BOOK_3;
        String id = "1";

        //when
        //then
        EditBookResponse actual = (EditBookResponse) bookService.editBook(id, bookRequest);

        assertNotNull(actual);
        assertTrue(expected.getLastEditDate().isBefore(actual.getLastEditDate()));
        assertEquals(expected, actual);
    }

    @Test
    @Tag(TagGroup.EDIT_BOOK)
    void shouldNotModifyBook() throws IllegalAccessException {
        //given
        Book bookToEdit = BookExamplesTest.copy(BookExamplesTest.VALID_BOOK_1);
        EditBookResponse expected = EditBookResponseExamplesTest.getEditBookResponse(false, BookResponseExamplesTest.VALID_BOOK_1);
        when(bookRepository.findById(any())).thenReturn(Optional.of(bookToEdit));
        BookRequest bookRequest = BookRequestExamplesTest.VALID_BOOK_1;
        String id = "1";

        //when
        //then
        EditBookResponse actual = (EditBookResponse) bookService.editBook(id, bookRequest);

        assertNotNull(actual);
        assertEquals(expected.getLastEditDate(), actual.getLastEditDate());
        assertEquals(expected.isModified(), actual.isModified());
    }

    @Test
    @Tag(TagGroup.EDIT_BOOK)
    void shouldThrowIllegalArgumentException_editBook() {
        //given
        BookRequest bookRequest = BookRequestExamplesTest.VALID_BOOK_3;

        //when
        //then
        assertThrows(IllegalArgumentException.class, () -> bookService.editBook(null, bookRequest));
        assertThrows(IllegalArgumentException.class, () -> bookService.editBook("", bookRequest));
        assertThrows(IllegalArgumentException.class, () -> bookService.editBook(" ", bookRequest));
    }

    @Test
    @Tag(TagGroup.EDIT_BOOK)
    void shouldThrowNoSuchElementException_editBook() {
        //given
        BookRequest bookRequest = BookRequestExamplesTest.VALID_BOOK_3;
        when(bookRepository.findById(any())).thenReturn(Optional.empty());

        //when
        //then
        assertThrows(NoSuchElementException.class, () -> bookService.editBook("1", bookRequest));
    }

    @Test
    @Tag(TagGroup.DELETE_BOOK)
    void shouldPutDeleteDate() {
        //given
        Book book = BookExamplesTest.copy(BookExamplesTest.VALID_BOOK_1);
        String bookId = "1";
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

        //when
        bookService.deleteBook(bookId);

        //then
        verify(bookRepository).save(bookArgumentCaptor.capture());
        Book actual = bookArgumentCaptor.getValue();

        assertNotNull(actual);
        assertNotNull(actual.getDeletedDate());
    }

    @Test
    @Tag(TagGroup.DELETE_BOOK)
    void shouldThrowIllegalArgumentException_deleteBook() {
        //given
        //when
        //then
        assertThrows(IllegalArgumentException.class, () -> bookService.deleteBook(null));
        assertThrows(IllegalArgumentException.class, () -> bookService.deleteBook(""));
        assertThrows(IllegalArgumentException.class, () -> bookService.deleteBook(" "));
    }

    @Test
    @Tag(TagGroup.DELETE_BOOK)
    void shouldThrowNoSuchElementException_deleteBook() {
        //given
        when(bookRepository.findById(any())).thenReturn(Optional.empty());

        //when
        //then
        assertThrows(NoSuchElementException.class, () -> bookService.deleteBook("1"));
    }

    @Test
    @Tag(TagGroup.GET_BOOK_LIST)
    void shouldReturnCorrectBookList() {
        //given
        List<Book> books = List.of(BookExamplesTest.VALID_BOOK_1, BookExamplesTest.VALID_BOOK_2);
        when(bookRepositoryTemplate.findBySearchTermAndPageRequest(any(), any())).thenReturn(books);
        BookResponseList expected = new BookResponseList(2L, 2L, List.of(BookResponseExamplesTest.VALID_BOOK_1, BookResponseExamplesTest.VALID_BOOK_2));
        //when
        BookResponseList actual = bookService.getBookList(null, null, null);
        //then
        assertNotNull(actual);
        assertEquals(2, actual.getBookResponseList().size());

        for (int i = 0; i < expected.getBookResponseList().size(); i++) {
            BookResponse expectedIteration = expected.getBookResponseList().get(i);
            BookResponse actualIteration = actual.getBookResponseList().get(i);
            assertEquals(expectedIteration.getId(), actualIteration.getId());
            assertEquals(expectedIteration.getCreatedDate(), actualIteration.getCreatedDate());
            assertEquals(expectedIteration.getLastEditDate(), actualIteration.getLastEditDate());
            assertEquals(expectedIteration.getDeletedDate(), actualIteration.getDeletedDate());
            assertEquals(expectedIteration.getIsbn(), actualIteration.getIsbn());
            assertEquals(expectedIteration.getTitle(), actualIteration.getTitle());
            assertEquals(expectedIteration.getAuthors(), actualIteration.getAuthors());
            assertEquals(expectedIteration.getDescription(), actualIteration.getDescription());
            assertEquals(expectedIteration.getCategories(), actualIteration.getCategories());
            assertEquals(expectedIteration.getPublisher(), actualIteration.getPublisher());
            assertEquals(expectedIteration.getPublishYear(), actualIteration.getPublishYear());
            assertEquals(expectedIteration.getPageCount(), actualIteration.getPageCount());
            assertEquals(expectedIteration.getLanguageCode(), actualIteration.getLanguageCode());
        }
    }
}
