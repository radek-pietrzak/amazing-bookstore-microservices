package com.productservice.example;

import com.productservice.api.response.BookResponseList;

import java.util.List;

public abstract class BookResponseListExample {

    public static BookResponseList getBookList() {
        return BookResponseList.builder()
                .bookQuantity(88)
                .bookQuantityOnPage(2)
                .bookResponseList(List.of(BookResponseExample.getValidBook1(), BookResponseExample.getValidBook2()))
                .build();
    }
}
