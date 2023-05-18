package com.productservice.api.response;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(exclude = {"lastEditDate"})
public class BookResponse implements Response{
    private String id;
    private LocalDateTime createdDate;
    private LocalDateTime lastEditDate;
    private LocalDateTime deletedDate;
    private String isbn;
    private String title;
    private List<AuthorResponse> authors;
    private String description;
    private List<String> categories;
    private PublisherResponse publisher;
    private Integer publishYear;
    private Integer pageCount;
    private String languageCode;
}
