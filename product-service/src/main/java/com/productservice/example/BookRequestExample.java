package com.productservice.example;

import com.productservice.api.request.AuthorRequest;
import com.productservice.api.request.BookRequest;
import com.productservice.api.request.PublisherRequest;

import java.util.List;

public interface BookRequestExample {

    BookRequest VALID_BOOK_1 = BookRequest.builder()
            .isbn("9780316769488")
            .title("The Catcher in the Rye")
            .description("The story of Holden Caulfield, a teenage boy who has been expelled from prep school and is wandering around New York City.")
            .publishYear(1951)
            .pageCount(224)
            .languageCode("en")
            .authors(List.of(new AuthorRequest("J.D.", "Salinger")))
            .categories(List.of("FICTION"))
            .publisher(new PublisherRequest("Little, Brown and Company", "Boston"))
            .build();

    List<AuthorRequest> VALID_AUTHOR_LIST = List.of(
            new AuthorRequest("J.D.", "Salinger"),
            new AuthorRequest("J.D.", null),
            new AuthorRequest("Another", "Author")
    );

    List<String> VALID_CATEGORY_LIST = List.of(
            "FICTION",
            "SCIENCE",
            "PHILOSOPHY",
            "MYSTERY_AND_THRILLER",
            "COMICS_AND_GRAPHIC_NOVELS"
    );

    BookRequest VALID_BOOK_2 = BookRequest.builder()
            .bookRequest(VALID_BOOK_1)
            .languageCode("pl")
            .authors(VALID_AUTHOR_LIST)
            .categories(VALID_CATEGORY_LIST)
            .publisher(new PublisherRequest("Little, Brown and Company", null))
            .build();

    BookRequest VALID_BOOK_3 = BookRequest.builder()
            .isbn("9781612620244")
            .title("Attack on Titan")
            .description("After a hundred years of peace, Titans suddenly take over the outermost human settlement, attacking and devouring its inhabitants. Eren Yeager, Mikasa Ackerman, and Armin Arlert join the Scout Regiment with dreams of exterminating Titans once and for all.")
            .publishYear(2012)
            .pageCount(208)
            .languageCode("jp")
            .authors(List.of(new AuthorRequest("Hajime Isayama", "Japanese manga artist and author, famous for creating the Attack on Titan manga series, which has sold over 100 million copies worldwide. Isayama's early inspirations include Tetsuo Hara's Fist of the North Star and the video game series Final Fantasy.")))
            .categories(List.of("COMICS_AND_GRAPHIC_NOVELS", "SCIENCE_FICTION_AND_FANTASY"))
            .publisher(new PublisherRequest("Kodansha Comics", "American subsidiary of Japanese publishing company Kodansha, specializing in manga publishing. With over 1000 titles in its catalog, it has become one of the largest English-language manga publishers in the United States"))
            .build();

    BookRequest BOOK_ALL_NULLS = new BookRequest();

    BookRequest BOOK_AUTHOR_NULLS = BookRequest.builder()
            .authors(List.of(new AuthorRequest()))
            .build();

    BookRequest BOOK_PUBLISHER_NULLS = BookRequest.builder()
            .publisher(new PublisherRequest())
            .build();

    BookRequest INVALID_ISBN_1 = BookRequest.builder()
            .bookRequest(VALID_BOOK_1)
            .isbn("9780316769489")
            .build();

    BookRequest INVALID_ISBN_2 = BookRequest.builder()
            .bookRequest(VALID_BOOK_1)
            .isbn("978031676948")
            .build();

    BookRequest INVALID_ISBN_3 = BookRequest.builder()
            .bookRequest(VALID_BOOK_1)
            .isbn("978031676948c")
            .build();

    BookRequest INVALID_ISBN_4 = BookRequest.builder()
            .bookRequest(VALID_BOOK_1)
            .isbn("")
            .build();

    BookRequest INVALID_TITLE_SIZE_MIN = BookRequest.builder()
            .bookRequest(VALID_BOOK_1)
            .title("")
            .build();

    BookRequest INVALID_TITLE_SIZE_MAX = BookRequest.builder()
            .bookRequest(VALID_BOOK_1)
            .title(new String(new char[256]).replace('\0', 'a'))
            .build();

    BookRequest INVALID_DESCRIPTION_SIZE_MIN = BookRequest.builder()
            .bookRequest(VALID_BOOK_1)
            .description("")
            .build();

    BookRequest INVALID_DESCRIPTION_SIZE_MAX = BookRequest.builder()
            .bookRequest(VALID_BOOK_1)
            .description(new String(new char[1001]).replace('\0', 'a'))
            .build();

    BookRequest INVALID_PAGE_COUNT_MIN_1 = BookRequest.builder()
            .bookRequest(VALID_BOOK_1)
            .pageCount(0)
            .build();

    BookRequest INVALID_PAGE_COUNT_MIN_2 = BookRequest.builder()
            .bookRequest(VALID_BOOK_1)
            .pageCount(-100)
            .build();

    BookRequest INVALID_PAGE_COUNT_MAX_1 = BookRequest.builder()
            .bookRequest(VALID_BOOK_1)
            .pageCount(10001)
            .build();

    BookRequest INVALID_PAGE_COUNT_MAX_2 = BookRequest.builder()
            .bookRequest(VALID_BOOK_1)
            .pageCount(2147483647)
            .build();

    BookRequest INVALID_LANG_CODE_1 = BookRequest.builder()
            .bookRequest(VALID_BOOK_1)
            .languageCode("xx")
            .build();

    BookRequest INVALID_LANG_CODE_2 = BookRequest.builder()
            .bookRequest(VALID_BOOK_1)
            .languageCode("eng")
            .build();

    BookRequest INVALID_LANG_CODE_3 = BookRequest.builder()
            .bookRequest(VALID_BOOK_1)
            .languageCode("")
            .build();

    BookRequest INVALID_AUTHOR_NAME_1 = BookRequest.builder()
            .bookRequest(VALID_BOOK_1)
            .authors(List.of(new AuthorRequest("", "description")))
            .build();

    BookRequest INVALID_AUTHOR_NAME_2 = BookRequest.builder()
            .bookRequest(VALID_BOOK_1)
            .authors(List.of(new AuthorRequest(new String(new char[256]).replace('\0', 'a'), "description")))
            .build();

    BookRequest INVALID_AUTHOR_DESCRIPTION_1 = BookRequest.builder()
            .bookRequest(VALID_BOOK_1)
            .authors(List.of(new AuthorRequest("Name", "")))
            .build();

    BookRequest INVALID_AUTHOR_DESCRIPTION_2 = BookRequest.builder()
            .bookRequest(VALID_BOOK_1)
            .authors(List.of(new AuthorRequest("Name", new String(new char[1001]).replace('\0', 'a'))))
            .build();

    BookRequest INVALID_CATEGORY_1 = BookRequest.builder()
            .bookRequest(VALID_BOOK_1)
            .categories(List.of("BAD_CATEGORY"))
            .build();

    BookRequest INVALID_CATEGORY_2 = BookRequest.builder()
            .bookRequest(VALID_BOOK_1)
            .categories(List.of(""))
            .build();

    BookRequest INVALID_CATEGORY_3 = BookRequest.builder()
            .bookRequest(VALID_BOOK_1)
            .categories(List.of("", "INVALID_CATEGORY"))
            .build();

    BookRequest INVALID_CATEGORY_4 = BookRequest.builder()
            .bookRequest(VALID_BOOK_1)
            .categories(List.of("FICTION", "INVALID_CATEGORY"))
            .build();

    BookRequest INVALID_PUBLISHER_NAME_LENGTH_1 = BookRequest.builder()
            .bookRequest(VALID_BOOK_1)
            .publisher(new PublisherRequest("", ""))
            .build();

    BookRequest INVALID_PUBLISHER_NAME_LENGTH_2 = BookRequest.builder()
            .bookRequest(VALID_BOOK_1)
            .publisher(new PublisherRequest(new String(new char[256]).replace('\0', 'a'), ""))
            .build();

    BookRequest INVALID_PUBLISHER_DESCRIPTION_LENGTH_1 = BookRequest.builder()
            .bookRequest(VALID_BOOK_1)
            .publisher(new PublisherRequest("Name", new String(new char[1001]).replace('\0', 'a')))
            .build();

}
