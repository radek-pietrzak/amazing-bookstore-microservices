package com.productservice.api.repository;

import com.productservice.entity.Author;
import com.productservice.entity.Book;
import com.productservice.entity.Category;
import com.productservice.entity.Publisher;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

public interface BookExamples {

    Book VALID_BOOK_1 = new Book.BookBuilder()
            .createdDate(LocalDateTime.of(2014, Month.JULY, 16, 10, 55, 22))
            .ISBN("9780316769488")
            .title("The Catcher in the Rye")
            .description("The story of Holden Caulfield, a teenage boy who has been expelled from prep school and is wandering around New York City.")
            .publishDate(LocalDate.of(1951, Month.JULY, 16))
            .pageCount(224)
            .languageCode("en")
            .authors(List.of(new Author("J.D.", "Salinger")))
            .categories(List.of(Category.FICTION))
            .publisher(new Publisher("Little, Brown and Company", "Boston"))
            .build();
}
