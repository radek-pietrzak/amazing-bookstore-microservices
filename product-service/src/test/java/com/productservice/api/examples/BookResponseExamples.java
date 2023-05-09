package com.productservice.api.examples;

import com.productservice.api.response.AuthorResponse;
import com.productservice.api.response.BookResponse;
import com.productservice.api.response.PublisherResponse;

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
            .publishYear(1951)
            .pageCount(224)
            .languageCode("en")
            .authors(List.of(new AuthorResponse("J.D.", "Salinger")))
            .categories(List.of("FICTION"))
            .publisher(new PublisherResponse("Little, Brown and Company", "Boston"))
            .build();

    List<AuthorResponse> VALID_AUTHOR_LIST = List.of(
            new AuthorResponse("J.D.", "Salinger"),
            new AuthorResponse("J.D.", null),
            new AuthorResponse("Another", "Author")
    );

    List<String> VALID_CATEGORY_LIST = List.of(
            "FICTION",
            "SCIENCE",
            "PHILOSOPHY",
            "MYSTERY_AND_THRILLER",
            "COMICS_AND_GRAPHIC_NOVELS"
    );

    BookResponse VALID_BOOK_2 = BookResponse.builder()
            .id("2")
            .createdDate(LocalDateTime.of(2014, 7, 16, 10, 55, 22))
            .lastEditDate(LocalDateTime.of(2023, 4, 16, 9, 59))
            .ISBN("9780316769488")
            .title("The Catcher in the Rye")
            .description("The story of Holden Caulfield, a teenage boy who has been expelled from prep school and is wandering around New York City.")
            .publishYear(1951)
            .pageCount(224)
            .languageCode("pl")
            .authors(VALID_AUTHOR_LIST)
            .categories(VALID_CATEGORY_LIST)
            .publisher(new PublisherResponse("Little, Brown and Company", null))
            .build();
}
