package com.productservice.api.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class BookResponse {
    private String id;
    private LocalDateTime createdDate;
    private LocalDateTime lastEditDate;
    private LocalDateTime deletedDate;
    private String ISBN;
    private String title;
    private List<AuthorResponse> authors;
    private String description;
    private List<String> categories;
    private PublisherResponse publisher;
    private Integer publishYear;
    private Integer pageCount;
    private String languageCode;
}
