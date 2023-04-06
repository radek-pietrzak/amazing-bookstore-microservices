package com.productservice.api.response;

import java.time.LocalDateTime;
import java.util.List;

public class BookResponse {
    private String id;
    private LocalDateTime createdDate;
    private LocalDateTime lastEditDate;
    private LocalDateTime deletedDate;
    private String ISBN;
    private String title;
    private String description;
    private LocalDateTime publishDate;
    private String pageCount;
    private String language;
    private List<AuthorResponse> authors;
    private List<String> categories;
    private PublisherResponse publisher;
}
