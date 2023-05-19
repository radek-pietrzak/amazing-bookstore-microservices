package com.productservice.mapper;

import com.productservice.api.example.BookExamplesTest;
import com.productservice.api.example.BookRequestExamplesTest;
import com.productservice.api.example.BookResponseExamplesTest;
import com.productservice.api.request.BookRequest;
import com.productservice.api.response.BookResponse;
import com.productservice.api.response.EditBookResponse;
import com.productservice.document.Book;
import com.productservice.api.example.EditBookResponseExamplesTest;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class BookMapperTest {
    BookMapper mapper = Mappers.getMapper(BookMapper.class);

    @Test
    void shouldMapCorrectBookToBookResponse() {
        //given
        Book book = BookExamplesTest.VALID_BOOK_1;
        BookResponse expected = BookResponseExamplesTest.VALID_BOOK_1;
        //when
        BookResponse actual = mapper.bookToBookResponse(book);
        //then
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void shouldMapCorrectBookRequestToBook() {
        //given
        BookRequest bookRequest = BookRequestExamplesTest.VALID_BOOK_1;
        Book expected = BookExamplesTest.VALID_BOOK_1;
        //when
        Book actual = mapper.bookRequestToBook(bookRequest);
        //then
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void shouldMapCorrectBookToEditBookResponse() {
        //given
        Book book = BookExamplesTest.VALID_BOOK_1;
        EditBookResponse expected = EditBookResponseExamplesTest.getEditBookResponse(true, BookResponseExamplesTest.VALID_BOOK_1);
        //when
        EditBookResponse actual = mapper.bookToEditBookResponse(true, book);
        //then
        assertNotNull(actual);
        assertEquals(expected, actual);
    }
}
