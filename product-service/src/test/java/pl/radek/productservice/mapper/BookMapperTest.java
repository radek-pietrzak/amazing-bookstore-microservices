package pl.radek.productservice.mapper;

import pl.radek.productservice.api.request.BookRequest;
import pl.radek.productservice.api.response.BookResponse;
import pl.radek.productservice.api.response.EditBookResponse;
import pl.radek.productservice.entity.Book;
import pl.radek.productservice.example.BookExample;
import pl.radek.productservice.example.BookRequestExample;
import pl.radek.productservice.example.BookResponseExample;
import pl.radek.productservice.example.EditBookResponseExample;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import pl.radek.productservice.mapper.BookMapper;

import static org.junit.jupiter.api.Assertions.*;

class BookMapperTest {
    BookMapper mapper = Mappers.getMapper(BookMapper.class);

    @Test
    void shouldMapCorrectBookToBookResponse() {
        //given
        Book book = BookExample.getValidBook1();
        BookResponse expected = BookResponseExample.getValidBook1();
        //when
        BookResponse actual = mapper.bookToBookResponse(book);
        //then
        assertNotNull(actual);
        assertEquals(expected.getIsbn(), actual.getIsbn());
        assertEquals(expected.getTitle(), actual.getTitle());
        assertEquals(expected.getAuthors().get(0), actual.getAuthors().get(0));
        assertEquals(expected.getDescription(), actual.getDescription());
        assertEquals(expected.getCategories().get(0), actual.getCategories().get(0));
        assertEquals(expected.getPublisher().getPublisherName(), actual.getPublisher().getPublisherName());
        assertEquals(expected.getPublisher().getPublisherDescription(), actual.getPublisher().getPublisherDescription());
        assertEquals(expected.getPublishYear(), actual.getPublishYear());
        assertEquals(expected.getPageCount(), actual.getPageCount());
        assertEquals(expected.getLanguageCode(), actual.getLanguageCode());
    }

    @Test
    void shouldMapCorrectBookRequestToBook() {
        //given
        BookRequest bookRequest = BookRequestExample.getValidBook1();
        Book expected = BookExample.getValidBook1();
        //when
        Book actual = mapper.bookRequestToBook(bookRequest);
        //then
        assertNotNull(actual);
        assertEquals(expected.getIsbn(), actual.getIsbn());
        assertEquals(expected.getTitle(), actual.getTitle());
        assertEquals(expected.getAuthors().get(0), actual.getAuthors().get(0));
        assertEquals(expected.getDescription(), actual.getDescription());
        assertEquals(expected.getCategories().get(0), actual.getCategories().get(0));
        assertEquals(expected.getPublisher().getPublisherName(), actual.getPublisher().getPublisherName());
        assertEquals(expected.getPublisher().getPublisherDescription(), actual.getPublisher().getPublisherDescription());
        assertEquals(expected.getPublishYear(), actual.getPublishYear());
        assertEquals(expected.getPageCount(), actual.getPageCount());
        assertEquals(expected.getLanguageCode(), actual.getLanguageCode());
    }

    @Test
    void shouldMapCorrectBookToEditBookResponse() {
        //given
        Book book = BookExample.getValidBook1();
        EditBookResponse expected = EditBookResponseExample.getEditBookResponse(true, BookResponseExample.getValidBook1());
        //when
        EditBookResponse actual = mapper.bookToEditBookResponse(true, book);
        //then
        assertNotNull(actual);
        assertEquals(expected.getIsbn(), actual.getIsbn());
        assertEquals(expected.getTitle(), actual.getTitle());
        assertEquals(expected.getAuthors().get(0), actual.getAuthors().get(0));
        assertEquals(expected.getDescription(), actual.getDescription());
        assertEquals(expected.getCategories().get(0), actual.getCategories().get(0));
        assertEquals(expected.getPublisher().getPublisherName(), actual.getPublisher().getPublisherName());
        assertEquals(expected.getPublisher().getPublisherDescription(), actual.getPublisher().getPublisherDescription());
        assertEquals(expected.getPublishYear(), actual.getPublishYear());
        assertEquals(expected.getPageCount(), actual.getPageCount());
        assertEquals(expected.getLanguageCode(), actual.getLanguageCode());
    }
}
