package com.productautofillservice.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class BookDetailsResponseList implements Response {
    private Integer numFound;
    List<BookDetailsResponse> bookDetailsResponseList;
}
