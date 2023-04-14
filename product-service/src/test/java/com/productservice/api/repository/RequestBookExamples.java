package com.productservice.api.repository;

import com.productservice.Category;
import com.productservice.api.request.AuthorRequest;
import com.productservice.api.request.BookRequest;
import com.productservice.api.request.PublisherRequest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

public interface RequestBookExamples {

    BookRequest SAVE_BOOK_1 = BookRequest.builder()
            .createdDate(LocalDateTime.of(2014, Month.JULY, 16, 10, 55, 22))
            .ISBN("9780316769488")
            .title("The Catcher in the Rye")
            .description("The story of Holden Caulfield, a teenage boy who has been expelled from prep school and is wandering around New York City.")
            .publishDate(LocalDate.of(1951, Month.JULY, 16))
            .pageCount("224")
            .language("English")
            .authors(List.of(new AuthorRequest("J.D.", "Salinger")))
            .categories(List.of(Category.FICTION.getName()))
            .publisher(new PublisherRequest("Little, Brown and Company", "Boston"))
            .build();
}
