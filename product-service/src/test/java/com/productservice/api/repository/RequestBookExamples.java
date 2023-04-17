package com.productservice.api.repository;

import com.productservice.Category;
import com.productservice.api.request.AuthorRequest;
import com.productservice.api.request.BookRequest;
import com.productservice.api.request.PublisherRequest;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

public interface RequestBookExamples {

    BookRequest SAVE_BOOK_1 = BookRequest.builder()
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

    BookRequest BOOK_ALL_NULLS = new BookRequest();

    BookRequest BOOK_AUTHOR_NULLS = BookRequest.builder()
            .authors(List.of(new AuthorRequest()))
            .build();

    BookRequest BOOK_PUBLISHER_NULLS = BookRequest.builder()
            .publisher(new PublisherRequest())
            .build();

    BookRequest INVALID_ISBN_1 = BookRequest.builder()
            .bookRequest(SAVE_BOOK_1)
            .ISBN("9780316769489")
            .build();

    BookRequest INVALID_ISBN_2 = BookRequest.builder()
            .bookRequest(SAVE_BOOK_1)
            .ISBN("978031676948")
            .build();

    BookRequest INVALID_ISBN_3 = BookRequest.builder()
            .bookRequest(SAVE_BOOK_1)
            .ISBN("978031676948c")
            .build();

    BookRequest INVALID_ISBN_4 = BookRequest.builder()
            .bookRequest(SAVE_BOOK_1)
            .ISBN("")
            .build();

    BookRequest INVALID_TITLE_SIZE_MIN = BookRequest.builder()
            .bookRequest(SAVE_BOOK_1)
            .title("")
            .build();

    BookRequest INVALID_TITLE_SIZE_MAX = BookRequest.builder()
            .bookRequest(SAVE_BOOK_1)
            .title(new String(new char[256]).replace('\0', 'a'))
            .build();

}
