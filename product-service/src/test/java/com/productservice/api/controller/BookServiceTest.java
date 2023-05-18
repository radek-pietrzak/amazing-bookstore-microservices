package com.productservice.api.controller;

import com.productservice.TagGroup;
import com.productservice.api.examples.BookRequestExamples;
import com.productservice.api.examples.BookResponseExamples;
import com.productservice.api.response.EditBookResponse;
import com.productservice.api.service.BookService;
import com.productservice.document.Book;
import com.productservice.api.examples.BookExamples;
import com.productservice.example.EditBookResponseExamples;
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
        BookResponse expected = bookPairResponse.bookResponse;
        when(bookRepository.findById(any())).thenReturn(Optional.ofNullable(bookPairResponse.book));
        //when
        BookResponse actual = bookService.getBook(any());
        //then
        assertNotNull(actual);
        assertNotNull(actual.getId());
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getCreatedDate(), actual.getCreatedDate());
        assertEquals(expected.getLastEditDate(), actual.getLastEditDate());
        assertEquals(expected.getDeletedDate(), actual.getDeletedDate());
        assertEquals(expected.getIsbn(), actual.getIsbn());
        assertEquals(expected.getTitle(), actual.getTitle());
        assertEquals(expected.getDescription(), actual.getDescription());
        assertEquals(expected.getPageCount(), actual.getPageCount());
        assertEquals(expected.getLanguageCode(), actual.getLanguageCode());
        assertEquals(expected.getAuthors(), actual.getAuthors());
        assertEquals(expected.getCategories(), actual.getCategories());
        assertEquals(expected.getPublisher(), actual.getPublisher());
        assertEquals(expected.getPublishYear(), actual.getPublishYear());
        assertEquals(expected, actual);
    }

    private static List<BookPairResponse> validBookPairResponseProvider() {
        return List.of(
                new BookPairResponse(BookResponseExamples.VALID_BOOK_1, BookExamples.VALID_BOOK_1),
                new BookPairResponse(BookResponseExamples.VALID_BOOK_2, BookExamples.VALID_BOOK_2)
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
        Book expectedBook = bookPairRequest.book;

        //when
        bookService.saveBook(request);

        //then
        verify(bookRepository).save(bookArgumentCaptor.capture());
        Book actualBook = bookArgumentCaptor.getValue();

        assertNotNull(actualBook);
        assertNotNull(expectedBook.getCreatedDate());
        assertInstanceOf(LocalDateTime.class, actualBook.getCreatedDate());
        assertEquals(expectedBook.getIsbn(), actualBook.getIsbn());
        assertEquals(expectedBook.getTitle(), actualBook.getTitle());
        assertEquals(expectedBook.getDescription(), actualBook.getDescription());
        assertEquals(expectedBook.getPublishYear(), actualBook.getPublishYear());
        assertEquals(expectedBook.getPageCount(), actualBook.getPageCount());
        assertEquals(expectedBook.getLanguageCode(), actualBook.getLanguageCode());
        assertNotNull(actualBook.getAuthors());
        assertEquals(expectedBook.getAuthors().size(), actualBook.getAuthors().size());
        assertEquals(expectedBook.getAuthors(), actualBook.getAuthors());
        assertEquals(expectedBook.getCategories(), actualBook.getCategories());
        assertEquals(expectedBook.getPublisher(), actualBook.getPublisher());
        assertEquals(expectedBook, actualBook);
    }

    private static List<BookPairRequest> validBookPairRequestsProvider() {
        return List.of(
                new BookPairRequest(BookRequestExamples.VALID_BOOK_1, BookExamples.VALID_BOOK_1),
                new BookPairRequest(BookRequestExamples.VALID_BOOK_2, BookExamples.VALID_BOOK_2)
        );
    }

    private record BookPairRequest(BookRequest bookRequest, Book book) {
    }

    @Test
    @Tag(TagGroup.EDIT_BOOK)
    void shouldReturnModifiedBook() throws IllegalAccessException {
        //given
        Book bookToEdit = BookExamples.copy(BookExamples.VALID_BOOK_1);
        EditBookResponse expected = EditBookResponseExamples.getEditBookResponse(true, BookResponseExamples.VALID_BOOK_3);
        when(bookRepository.findById(any())).thenReturn(Optional.of(bookToEdit));
        BookRequest bookRequest = BookRequestExamples.VALID_BOOK_3;
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
        Book bookToEdit = BookExamples.copy(BookExamples.VALID_BOOK_1);
        EditBookResponse expected = EditBookResponseExamples.getEditBookResponse(false, BookResponseExamples.VALID_BOOK_1);
        when(bookRepository.findById(any())).thenReturn(Optional.of(bookToEdit));
        BookRequest bookRequest = BookRequestExamples.VALID_BOOK_1;
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
    void shouldThrowIllegalArgumentException() {
        //given
        BookRequest bookRequest = BookRequestExamples.VALID_BOOK_3;

        //when
        //then
        assertThrows(IllegalArgumentException.class, () -> bookService.editBook(null, bookRequest));
        assertThrows(IllegalArgumentException.class, () -> bookService.editBook("", bookRequest));
        assertThrows(IllegalArgumentException.class, () -> bookService.editBook(" ", bookRequest));
    }

    @Test
    @Tag(TagGroup.EDIT_BOOK)
    void shouldThrowNoSuchElementException() {
        //given
        BookRequest bookRequest = BookRequestExamples.VALID_BOOK_3;
        when(bookRepository.findById(any())).thenReturn(Optional.empty());

        //when
        //then
        assertThrows(NoSuchElementException.class, () -> bookService.editBook("1", bookRequest));
    }

    @Test
    @Tag(TagGroup.DELETE_BOOK)
    void shouldPutDeleteDate() {
        //given
        Book book = BookExamples.copy(BookExamples.VALID_BOOK_1);
        when(bookRepository.findById(any())).thenReturn(Optional.of(book));

        //when
        bookService.deleteBook(any());

        //then
        verify(bookRepository).save(bookArgumentCaptor.capture());
        Book actual = bookArgumentCaptor.getValue();

        assertNotNull(actual);
        assertNotNull(actual.getDeletedDate());
    }

    @Test
    @Tag(TagGroup.GET_BOOK_LIST)
    void shouldReturnCorrectBookList() {
        //given
        List<Book> books = List.of(BookExamples.VALID_BOOK_1, BookExamples.VALID_BOOK_2);
        when(bookRepositoryTemplate.findBySearchTermAndPageRequest(any(), any())).thenReturn(books);
        BookResponseList expected = new BookResponseList(2L, 2L, List.of(BookResponseExamples.VALID_BOOK_1, BookResponseExamples.VALID_BOOK_2));
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
