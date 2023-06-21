package com.productservice.api.response;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class BookResponseList extends PageableResponse {
    private List<BookResponse> bookResponseList;
}
