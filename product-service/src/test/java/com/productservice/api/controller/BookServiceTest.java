package com.productservice.api.controller;

import com.productservice.api.service.BookService;
import com.productservice.entity.Book;
import com.productservice.api.repository.BookExamples;
import com.productservice.repository.BookRepository;
import com.productservice.api.repository.BookRequestExamples;
import com.productservice.api.request.BookRequest;
import com.productservice.api.response.BookResponse;
import com.productservice.api.response.BookResponseList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @InjectMocks
    private BookService bookService;
    @Mock
    private BookRepository bookRepositoryMock;
    @Captor
    private ArgumentCaptor<Book> bookArgumentCaptor;

    @BeforeEach
    public void setUp() {
        bookService = new BookService(bookRepositoryMock);
    }

    @Test
    void getBook_shouldReturnNotNullBookResponseIfBookIdExists() {
        BookResponse actual = bookService.getBook("1");
        assertNotNull(actual);
    }

    @Test
    void saveBook_shouldBuildCorrectBook() {
        //given
        BookRequest request = BookRequestExamples.VALID_BOOK_1;
        Book expectedBook = BookExamples.VALID_BOOK_1;

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
        assertEquals(expectedBook.getPublishDate(), actualBook.getPublishDate());
        assertEquals(expectedBook.getPageCount(), actualBook.getPageCount());
        assertEquals(expectedBook.getLanguageCode(), actualBook.getLanguageCode());
        assertNotNull(actualBook.getAuthors());
        assertEquals(expectedBook.getAuthors().size(), actualBook.getAuthors().size());
        assertEquals(expectedBook.getAuthors(), actualBook.getAuthors());
        assertEquals(expectedBook.getCategories(), actualBook.getCategories());
        assertEquals(expectedBook.getPublisher(), actualBook.getPublisher());
        assertEquals(expectedBook, actualBook);
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
    void getBookList_shouldReturnCorrectBookList() {
        BookResponseList actual = bookService.getBookList("");
        assertNotNull(actual);
    }
}
