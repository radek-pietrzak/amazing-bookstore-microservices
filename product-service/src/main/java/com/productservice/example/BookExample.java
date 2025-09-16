package com.productservice.example;

import com.productservice.entity.Author;
import com.productservice.entity.Book;
import com.productservice.entity.Publisher;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static com.productservice.entity.Category.*;

public abstract class BookExample {

    public static Book getValidBook1() {
        return Book.builder()
                .id(1L)
                .createdDate(LocalDateTime.of(2014, Month.JULY, 16, 10, 55, 22))
                .lastEditDate(LocalDateTime.of(2023, 4, 16, 9, 59))
                .isbn("9780316769488")
                .title("The Catcher in the Rye")
                .description("The story of Holden Caulfield, a teenage boy who has been expelled from prep school and is wandering around New York City.")
                .publishYear(1951)
                .pageCount(224)
                .languageCode("en")
                .authors(List.of(new Author("J.D.", "Salinger")))
                .categories(List.of(FICTION))
                .publisher(new Publisher("Little, Brown and Company", "Boston"))
                .build();
    }

    public static Book getValidBook2() {
        Book book = getValidBook1();
        book.setId(2L);
        book.setLanguageCode("pl");
        book.setAuthors(List.of(
                new Author("J.D.", "Salinger"),
                new Author("J.D.", null),
                new Author("Another", "Author")
        ));
        book.setCategories(List.of(
                FICTION,
                SCIENCE,
                PHILOSOPHY,
                MYSTERY_AND_THRILLER,
                COMICS_AND_GRAPHIC_NOVELS
        ));
        book.setPublisher(new Publisher("Little, Brown and Company", null));
        return book;
    }
}
