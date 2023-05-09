package com.productservice.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class BookResponseList {
    private long bookQuantity;
    private long bookQuantityOnPage;
    private List<BookResponse> bookResponseList;
}
