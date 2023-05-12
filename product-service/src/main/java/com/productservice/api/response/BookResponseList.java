package com.productservice.api.response;

import lombok.*;

import java.util.List;
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class BookResponseList {
    private long bookQuantity;
    private long bookQuantityOnPage;
    private List<BookResponse> bookResponseList;
}
