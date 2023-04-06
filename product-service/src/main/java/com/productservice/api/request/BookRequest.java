package com.productservice.api.request;

import java.time.LocalDateTime;
import java.util.List;

public class BookRequest {
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
    private List<AuthorRequest> authors;
    private List<String> categories;
    private PublisherRequest publisher;
}
