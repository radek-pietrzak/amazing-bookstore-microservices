package com.productservice.api.controller;

import com.productservice.TagGroup;
import com.productservice.api.examples.BookRequestExamples;
import com.productservice.api.examples.BookResponseExamples;
import com.productservice.api.service.BookService;
import com.productservice.document.Book;
import com.productservice.api.examples.BookExamples;
import com.productservice.repository.BookRepository;
import com.productservice.api.request.BookRequest;
import com.productservice.api.response.BookResponse;
import com.productservice.api.response.BookResponseList;
import com.productservice.repository.BookRepositoryTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import jakarta.validation.Validator;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @InjectMocks
    private BookService bookService;
    @Mock
    private BookRepository bookRepositoryMock;
    @Mock
    private BookRepositoryTemplate bookRepositoryTemplateMock;
    @Captor
    private ArgumentCaptor<Book> bookArgumentCaptor;
    @Mock
    private Validator validator;

    @BeforeEach
    public void setUp() {
        bookService = new BookService(bookRepositoryMock, bookRepositoryTemplateMock, validator);
    }

    @Test
    void getBook_shouldReturnNotNullBookResponseIfBookIdExists() {
        BookResponse actual = bookService.getBook("1");
        assertNotNull(actual);
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
        verify(bookRepositoryMock).save(bookArgumentCaptor.capture());
        Book actualBook = bookArgumentCaptor.getValue();

        assertNotNull(actualBook);
        assertNotNull(expectedBook.getCreatedDate());
        assertInstanceOf(LocalDateTime.class, actualBook.getCreatedDate());
        assertEquals(expectedBook.getISBN(), actualBook.getISBN());
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
    void editBook_shouldBuildCorrectBook() {
        assertNotNull(null);
    }

    @Test
    void deleteBook_shouldPutDeleteDate() {
        assertNotNull(null);
    }

    @Test
    @Tag(TagGroup.GET_BOOK_LIST)
    void shouldReturnCorrectBookList() {
        //given
        List<Book> books = List.of(BookExamples.VALID_BOOK_1, BookExamples.VALID_BOOK_2);
        when(bookRepositoryTemplateMock.findBySearchTermAndPageRequest(any(), any())).thenReturn(books);
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
            assertEquals(expectedIteration.getISBN(), actualIteration.getISBN());
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
