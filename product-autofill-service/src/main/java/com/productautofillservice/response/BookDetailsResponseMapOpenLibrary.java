package com.productautofillservice.response;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class BookDetailsResponseMapOpenLibrary implements Response {
    private Integer numFound;
    Map<String, BookDetailsResponseOpenLibrary> bookDetailsResponseOpenLibraryMap;
}
