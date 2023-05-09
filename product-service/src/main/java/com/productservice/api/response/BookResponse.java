package com.productservice.api.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class BookResponse {
    private String id;
    private LocalDateTime createdDate;
    private LocalDateTime lastEditDate;
    private LocalDateTime deletedDate;
    private String ISBN;
    private String title;
    private String description;
    private Integer pageCount;
    private String languageCode;
    private List<AuthorResponse> authors;
    private List<String> categories;
    private PublisherResponse publisher;
    private Integer publishYear;

}
