package com.productservice.api.repository;

import com.productservice.api.response.AuthorResponse;
import com.productservice.api.response.BookResponse;
import com.productservice.api.response.PublisherResponse;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface BookResponseExamples {

    BookResponse VALID_BOOK_1 = BookResponse.builder()
            .id("1")
            .createdDate(LocalDateTime.of(2014, 7, 16, 10, 55, 22))
            .lastEditDate(LocalDateTime.of(2023, 4, 16, 9, 59))
            .ISBN("9780316769488")
            .title("The Catcher in the Rye")
            .description("The story of Holden Caulfield, a teenage boy who has been expelled from prep school and is wandering around New York City.")
            .publishDate(LocalDate.of(1951, 7, 16))
            .pageCount(224)
            .languageCode("en")
            .authors(List.of(new AuthorResponse("J.D.", "Salinger")))
            .categories(List.of("FICTION"))
            .publisher(new PublisherResponse("Little, Brown and Company", "Boston"))
            .build();
}
