package com.productservice.mapper;

import com.productservice.api.examples.BookExamples;
import com.productservice.api.examples.BookRequestExamples;
import com.productservice.api.examples.BookResponseExamples;
import com.productservice.api.request.BookRequest;
import com.productservice.api.response.BookResponse;
import com.productservice.document.Book;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class BookMapperTest {
    BookMapper mapper = Mappers.getMapper(BookMapper.class);

    @Test
    void shouldMapCorrectBookToBookResponse() {
        //given
        Book book = BookExamples.VALID_BOOK_1;
        BookResponse expected = BookResponseExamples.VALID_BOOK_1;
        //when
        BookResponse actual = mapper.bookToBookResponse(book);
        //then
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void shouldMapCorrectBookRequestToBook() {
        //given
        BookRequest bookRequest = BookRequestExamples.VALID_BOOK_1;
        Book expected = BookExamples.VALID_BOOK_1;
        //when
        Book actual = mapper.bookRequestToBook(bookRequest);
        //then
        assertNotNull(actual);
        assertEquals(expected, actual);
    }
}
