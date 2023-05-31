package com.productservice.example;

import com.productservice.document.Author;
import com.productservice.document.Book;
import com.productservice.document.Category;
import com.productservice.document.Publisher;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static com.productservice.document.Category.*;
// TODO change book example provider
public interface BookExamples {

    Book VALID_BOOK_1 = Book.builder()
            .id("1")
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

    Book VALID_BOOK_2 = Book.builder(VALID_BOOK_1)
            .id("2")
            .languageCode("pl")
            .authors(VALID_AUTHOR_LIST)
            .categories(VALID_CATEGORY_LIST)
            .publisher(new Publisher("Little, Brown and Company", null))
            .build();

    Book VALID_BOOK_3 = Book.builder()
            .id("1")
            .createdDate(LocalDateTime.of(2014, Month.JULY, 16, 10, 55, 22))
            .lastEditDate(LocalDateTime.of(2023, 4, 16, 9, 59))
            .isbn("9781612620244")
            .title("Attack on Titan")
            .description("After a hundred years of peace, Titans suddenly take over the outermost human settlement, attacking and devouring its inhabitants. Eren Yeager, Mikasa Ackerman, and Armin Arlert join the Scout Regiment with dreams of exterminating Titans once and for all.")
            .publishYear(2012)
            .pageCount(208)
            .languageCode("jp")
            .authors(List.of(new Author("Hajime Isayama", "Japanese manga artist and author, famous for creating the Attack on Titan manga series, which has sold over 100 million copies worldwide. Isayama's early inspirations include Tetsuo Hara's Fist of the North Star and the video game series Final Fantasy.")))
            .categories(List.of(COMICS_AND_GRAPHIC_NOVELS, SCIENCE_FICTION_AND_FANTASY))
            .publisher(new Publisher("Kodansha Comics", "American subsidiary of Japanese publishing company Kodansha, specializing in manga publishing. With over 1000 titles in its catalog, it has become one of the largest English-language manga publishers in the United States"))
            .build();

    static Book copy(Book book) {
        return Book.builder(book).build();
    }
}
