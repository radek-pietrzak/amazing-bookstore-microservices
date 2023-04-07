package com.productservice.api.controller;

import com.productservice.api.response.BookResponse;
import com.productservice.api.response.BookResponseList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;


@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    private BookService bookService;
    @BeforeEach
    public void setUp() {
        bookService = new BookService();
    }

    @Test
    void getBook_shouldReturnNotNullBookResponseIfBookIdExists() {
        BookResponse actual = bookService.getBook("1");
        assertNotNull(actual);
    }

    @Test
    void saveBook_shouldBuildCorrectBook() {
        assertNotNull(null);
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
