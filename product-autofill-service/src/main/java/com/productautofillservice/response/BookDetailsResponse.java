package com.productautofillservice.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class BookDetailsResponse {
    private String isbn;
    private String title;
    private List<String> authors;
    private Integer publishYear;
    private List<String> publishers;
    private int numberOfPages;
    private List<String> languages;
    private List<String> subjects;
    private String description;
}
