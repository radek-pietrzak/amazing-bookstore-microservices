package com.productservice.example;

import com.productservice.api.response.BookResponseList;

import java.util.List;

public interface BookResponseListExample {

    BookResponseList BOOK_LIST = BookResponseList.builder()
            .bookQuantity(88)
            .bookQuantityOnPage(2)
            .bookResponseList(List.of(BookResponseExample.VALID_BOOK_1, BookResponseExample.VALID_BOOK_2))
            .build();
}
