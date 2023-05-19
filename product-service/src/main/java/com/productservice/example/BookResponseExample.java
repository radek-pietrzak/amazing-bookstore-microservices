package com.productservice.example;

import com.productservice.api.response.AuthorResponse;
import com.productservice.api.response.BookResponse;
import com.productservice.api.response.PublisherResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface BookResponseExample {

    BookResponse VALID_BOOK_1 = BookResponse.builder()
            .id("1")
            .createdDate(LocalDateTime.of(2014, 7, 16, 10, 55, 22))
            .lastEditDate(LocalDateTime.of(2023, 4, 16, 9, 59))
            .isbn("9780316769488")
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
            .isbn("9780316769488")
            .title("The Catcher in the Rye")
            .description("The story of Holden Caulfield, a teenage boy who has been expelled from prep school and is wandering around New York City.")
            .publishYear(1951)
            .pageCount(224)
            .languageCode("pl")
            .authors(VALID_AUTHOR_LIST)
            .categories(VALID_CATEGORY_LIST)
            .publisher(new PublisherResponse("Little, Brown and Company", null))
            .build();

    BookResponse VALID_BOOK_3 = BookResponse.builder()
            .id("1")
            .createdDate(LocalDateTime.of(2014, 7, 16, 10, 55, 22))
            .lastEditDate(LocalDateTime.of(2023, 4, 16, 9, 59))
            .isbn("9781612620244")
            .title("Attack on Titan")
            .description("After a hundred years of peace, Titans suddenly take over the outermost human settlement, attacking and devouring its inhabitants. Eren Yeager, Mikasa Ackerman, and Armin Arlert join the Scout Regiment with dreams of exterminating Titans once and for all.")
            .publishYear(2012)
            .pageCount(208)
            .languageCode("jp")
            .authors(List.of(new AuthorResponse("Hajime Isayama", "Japanese manga artist and author, famous for creating the Attack on Titan manga series, which has sold over 100 million copies worldwide. Isayama's early inspirations include Tetsuo Hara's Fist of the North Star and the video game series Final Fantasy.")))
            .categories(List.of("COMICS_AND_GRAPHIC_NOVELS", "SCIENCE_FICTION_AND_FANTASY"))
            .publisher(new PublisherResponse("Kodansha Comics", "American subsidiary of Japanese publishing company Kodansha, specializing in manga publishing. With over 1000 titles in its catalog, it has become one of the largest English-language manga publishers in the United States"))
            .build();
}
