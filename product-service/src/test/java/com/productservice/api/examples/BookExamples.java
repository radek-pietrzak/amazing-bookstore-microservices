package com.productservice.api.examples;

import com.productservice.entity.Author;
import com.productservice.entity.Book;
import com.productservice.entity.Category;
import com.productservice.entity.Publisher;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static com.productservice.entity.Category.*;

public interface BookExamples {

    Book VALID_BOOK_1 = new Book.BookBuilder()
            .id("1")
            .createdDate(LocalDateTime.of(2014, Month.JULY, 16, 10, 55, 22))
            .lastEditDate(LocalDateTime.of(2023, 4, 16, 9, 59))
            .ISBN("9780316769488")
            .title("The Catcher in the Rye")
            .description("The story of Holden Caulfield, a teenage boy who has been expelled from prep school and is wandering around New York City.")
            .publishYear(1951)
            .pageCount(224)
            .languageCode("en")
            .authors(List.of(new Author("J.D.", "Salinger")))
            .categories(List.of(FICTION))
            .publisher(new Publisher("Little, Brown and Company", "Boston"))
            .build();

    List<Author> VALID_AUTHOR_LIST = List.of(
            new Author("J.D.", "Salinger"),
            new Author("J.D.", null),
            new Author("Another", "Author")
    );

    List<Category> VALID_CATEGORY_LIST = List.of(
            FICTION,
            SCIENCE,
            PHILOSOPHY,
            MYSTERY_AND_THRILLER,
            COMICS_AND_GRAPHIC_NOVELS
    );

    Book VALID_BOOK_2 = new Book.BookBuilder(VALID_BOOK_1)
            .id("2")
            .languageCode("pl")
            .authors(VALID_AUTHOR_LIST)
            .categories(VALID_CATEGORY_LIST)
            .publisher(new Publisher("Little, Brown and Company", null))
            .build();
}
