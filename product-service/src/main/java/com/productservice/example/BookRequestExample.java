package com.productservice.example;

import com.productservice.api.request.AuthorRequest;
import com.productservice.api.request.BookRequest;
import com.productservice.api.request.PublisherRequest;

import java.util.List;

public abstract class BookRequestExample {

    public static BookRequest getValidBook1() {
        return BookRequest.builder()
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

    }

    public static BookRequest getValidBook2() {
        BookRequest bookRequest = getValidBook1();
        bookRequest.setLanguageCode("pl");
        bookRequest.setAuthors(List.of(
                new AuthorRequest("J.D.", "Salinger"),
                new AuthorRequest("J.D.", null),
                new AuthorRequest("Another", "Author")
        ));
        bookRequest.setCategories(List.of(
                "FICTION",
                "SCIENCE",
                "PHILOSOPHY",
                "MYSTERY_AND_THRILLER",
                "COMICS_AND_GRAPHIC_NOVELS"
        ));
        bookRequest.setPublisher(new PublisherRequest("Little, Brown and Company", null));
        return bookRequest;
    }


    public static BookRequest getValidBook3() {
        return BookRequest.builder()
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

    }

    public static BookRequest getBookAllNulls() {
        return new BookRequest();
    }

    public static BookRequest getBookAuthorNulls() {
        BookRequest bookRequest = getValidBook1();
        bookRequest.setAuthors(List.of(new AuthorRequest()));
        return bookRequest;
    }

    public static BookRequest getBookPublisherNulls() {
        BookRequest bookRequest = getValidBook1();
        bookRequest.setPublisher(new PublisherRequest());
        return bookRequest;
    }

    public static BookRequest getInvalidISBN1() {
        BookRequest bookRequest = getValidBook1();
        bookRequest.setIsbn("9780316769489");
        return bookRequest;
    }

    public static BookRequest getInvalidISBN2() {
        BookRequest bookRequest = getValidBook1();
        bookRequest.setIsbn("978031676948");
        return bookRequest;
    }

    public static BookRequest getInvalidISBN3() {
        BookRequest bookRequest = getValidBook1();
        bookRequest.setIsbn("978031676948c");
        return bookRequest;
    }

    public static BookRequest getInvalidISBN4() {
        BookRequest bookRequest = getValidBook1();
        bookRequest.setIsbn("");
        return bookRequest;
    }

    public static BookRequest getInvalidTitleSizeMin() {
        BookRequest bookRequest = getValidBook1();
        bookRequest.setTitle("");
        return bookRequest;
    }

    public static BookRequest getInvalidTitleSizeMax() {
        BookRequest bookRequest = getValidBook1();
        bookRequest.setTitle(new String(new char[256]).replace('\0', 'a'));
        return bookRequest;
    }

    public static BookRequest getInvalidDescriptionSizeMin() {
        BookRequest bookRequest = getValidBook1();
        bookRequest.setDescription("");
        return bookRequest;
    }

    public static BookRequest getInvalidDescriptionSizeMax() {
        BookRequest bookRequest = getValidBook1();
        bookRequest.setDescription(new String(new char[1001]).replace('\0', 'a'));
        return bookRequest;
    }

    public static BookRequest getInvalidPageCountMin1() {
        BookRequest bookRequest = getValidBook1();
        bookRequest.setPageCount(0);
        return bookRequest;
    }

    public static BookRequest getInvalidPageCountMin2() {
        BookRequest bookRequest = getValidBook1();
        bookRequest.setPageCount(-100);
        return bookRequest;
    }

    public static BookRequest getInvalidPageCountMax1() {
        BookRequest bookRequest = getValidBook1();
        bookRequest.setPageCount(10001);
        return bookRequest;
    }

    public static BookRequest getInvalidPageCountMax2() {
        BookRequest bookRequest = getValidBook1();
        bookRequest.setPageCount(2147483647);
        return bookRequest;
    }

    public static BookRequest getInvalidLangCode1() {
        BookRequest bookRequest = getValidBook1();
        bookRequest.setLanguageCode("xx");
        return bookRequest;
    }

    public static BookRequest getInvalidLangCode2() {
        BookRequest bookRequest = getValidBook1();
        bookRequest.setLanguageCode("eng");
        return bookRequest;
    }

    public static BookRequest getInvalidLangCode3() {
        BookRequest bookRequest = getValidBook1();
        bookRequest.setLanguageCode("");
        return bookRequest;
    }

    public static BookRequest getInvalidAuthorName1() {
        BookRequest bookRequest = getValidBook1();
        bookRequest.setAuthors(List.of(new AuthorRequest("", "description")));
        return bookRequest;
    }

    public static BookRequest getInvalidAuthorName2() {
        BookRequest bookRequest = getValidBook1();
        bookRequest.setAuthors(List.of(new AuthorRequest(new String(new char[256]).replace('\0', 'a'), "description")));
        return bookRequest;
    }

    public static BookRequest getInvalidAuthorDescription1() {
        BookRequest bookRequest = getValidBook1();
        bookRequest.setAuthors(List.of(new AuthorRequest("Name", "")));
        return bookRequest;
    }

    public static BookRequest getInvalidAuthorDescription2() {
        BookRequest bookRequest = getValidBook1();
        bookRequest.setAuthors(List.of(new AuthorRequest("Name", new String(new char[1001]).replace('\0', 'a'))));
        return bookRequest;
    }

    public static BookRequest getInvalidCategory1() {
        BookRequest bookRequest = getValidBook1();
        bookRequest.setCategories(List.of("BAD_CATEGORY"));
        return bookRequest;
    }

    public static BookRequest getInvalidCategory2() {
        BookRequest bookRequest = getValidBook1();
        bookRequest.setCategories(List.of(""));
        return bookRequest;
    }

    public static BookRequest getInvalidCategory3() {
        BookRequest bookRequest = getValidBook1();
        bookRequest.setCategories(List.of("", "INVALID_CATEGORY"));
        return bookRequest;
    }

    public static BookRequest getInvalidCategory4() {
        BookRequest bookRequest = getValidBook1();
        bookRequest.setCategories(List.of("FICTION", "INVALID_CATEGORY"));
        return bookRequest;
    }

    public static BookRequest getInvalidPublisherNameLength1() {
        BookRequest bookRequest = getValidBook1();
        bookRequest.setPublisher(new PublisherRequest("", ""));
        return bookRequest;
    }

    public static BookRequest getInvalidPublisherNameLength2() {
        BookRequest bookRequest = getValidBook1();
        bookRequest.setPublisher(new PublisherRequest(new String(new char[256]).replace('\0', 'a'), ""));
        return bookRequest;
    }

    public static BookRequest getInvalidPublisherDescriptionLength1() {
        BookRequest bookRequest = getValidBook1();
        bookRequest.setPublisher(new PublisherRequest("Name", new String(new char[1001]).replace('\0', 'a')));
        return bookRequest;
    }
}
