package com.productservice.api.repository;

import com.productservice.entity.Category;
import com.productservice.api.request.AuthorRequest;
import com.productservice.api.request.BookRequest;
import com.productservice.api.request.PublisherRequest;

import java.util.List;

public interface BookRequestExamples {

    BookRequest SAVE_BOOK_1 = BookRequest.builder()
            .ISBN("9780316769488")
            .title("The Catcher in the Rye")
            .description("The story of Holden Caulfield, a teenage boy who has been expelled from prep school and is wandering around New York City.")
            .publishDate("1951-07-16")
            .pageCount(224)
            .languageCode("English")
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

    BookRequest INVALID_DESCRIPTION_SIZE_MIN = BookRequest.builder()
            .bookRequest(SAVE_BOOK_1)
            .description("")
            .build();

    BookRequest INVALID_DESCRIPTION_SIZE_MAX = BookRequest.builder()
            .bookRequest(SAVE_BOOK_1)
            .description(new String(new char[1001]).replace('\0', 'a'))
            .build();

    BookRequest INVALID_LOCAL_DATE_FORMAT_1 = BookRequest.builder()
            .bookRequest(SAVE_BOOK_1)
            .publishDate("2022/01/01")
            .build();

    BookRequest INVALID_LOCAL_DATE_FORMAT_2 = BookRequest.builder()
            .bookRequest(SAVE_BOOK_1)
            .publishDate("22-01-01")
            .build();

    BookRequest INVALID_LOCAL_DATE_FORMAT_3 = BookRequest.builder()
            .bookRequest(SAVE_BOOK_1)
            .publishDate("2022-13-01")
            .build();

    BookRequest INVALID_LOCAL_DATE_FORMAT_4 = BookRequest.builder()
            .bookRequest(SAVE_BOOK_1)
            .publishDate("2022-02-30")
            .build();

    BookRequest INVALID_LOCAL_DATE_FORMAT_5 = BookRequest.builder()
            .bookRequest(SAVE_BOOK_1)
            .publishDate("2022-02-01T10:00:00")
            .build();

    BookRequest INVALID_LOCAL_DATE_FORMAT_6 = BookRequest.builder()
            .bookRequest(SAVE_BOOK_1)
            .publishDate("abc")
            .build();

    BookRequest INVALID_PAGE_COUNT_MIN_1 = BookRequest.builder()
            .bookRequest(SAVE_BOOK_1)
            .pageCount(0)
            .build();

    BookRequest INVALID_PAGE_COUNT_MIN_2 = BookRequest.builder()
            .bookRequest(SAVE_BOOK_1)
            .pageCount(-100)
            .build();

    BookRequest INVALID_PAGE_COUNT_MAX_1 = BookRequest.builder()
            .bookRequest(SAVE_BOOK_1)
            .pageCount(10001)
            .build();

    BookRequest INVALID_PAGE_COUNT_MAX_2 = BookRequest.builder()
            .bookRequest(SAVE_BOOK_1)
            .pageCount(2147483647)
            .build();

    BookRequest INVALID_LANG_CODE_1 = BookRequest.builder()
            .bookRequest(SAVE_BOOK_1)
            .languageCode("xx")
            .build();

    BookRequest INVALID_LANG_CODE_2 = BookRequest.builder()
            .bookRequest(SAVE_BOOK_1)
            .languageCode("eng")
            .build();

    BookRequest INVALID_LANG_CODE_3 = BookRequest.builder()
            .bookRequest(SAVE_BOOK_1)
            .languageCode("")
            .build();

    BookRequest INVALID_AUTHOR_NAME_1 = BookRequest.builder()
            .bookRequest(SAVE_BOOK_1)
            .authors(List.of(new AuthorRequest("", "description")))
            .build();

    BookRequest INVALID_AUTHOR_NAME_2 = BookRequest.builder()
            .bookRequest(SAVE_BOOK_1)
            .authors(List.of(new AuthorRequest(new String(new char[256]).replace('\0', 'a'), "description")))
            .build();

    BookRequest INVALID_AUTHOR_DESCRIPTION_1 = BookRequest.builder()
            .bookRequest(SAVE_BOOK_1)
            .authors(List.of(new AuthorRequest("Name", "")))
            .build();

    BookRequest INVALID_AUTHOR_DESCRIPTION_2 = BookRequest.builder()
            .bookRequest(SAVE_BOOK_1)
            .authors(List.of(new AuthorRequest("Name", new String(new char[1001]).replace('\0', 'a'))))
            .build();

    BookRequest INVALID_CATEGORY_1 = BookRequest.builder()
            .bookRequest(SAVE_BOOK_1)
            .categories(List.of("BAD_CATEGORY"))
            .build();

    BookRequest INVALID_CATEGORY_2 = BookRequest.builder()
            .bookRequest(SAVE_BOOK_1)
            .categories(List.of(""))
            .build();

    BookRequest INVALID_CATEGORY_3 = BookRequest.builder()
            .bookRequest(SAVE_BOOK_1)
            .categories(List.of("", "INVALID_CATEGORY"))
            .build();

    BookRequest INVALID_PUBLISHER_NAME_1 = BookRequest.builder()
            .bookRequest(SAVE_BOOK_1)
            .publisher(new PublisherRequest("", ""))
            .build();

    BookRequest INVALID_PUBLISHER_NAME_2 = BookRequest.builder()
            .bookRequest(SAVE_BOOK_1)
            .publisher(new PublisherRequest(new String(new char[256]).replace('\0', 'a'), ""))
            .build();

}
