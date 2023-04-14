package com.productservice.api.response;

import java.time.LocalDate;
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
    private String pageCount;
    private String language;
    private List<AuthorResponse> authors;
    private List<String> categories;
    private PublisherResponse publisher;
    private LocalDate publishDate;

    public BookResponse(String id, LocalDateTime createdDate, LocalDateTime lastEditDate, LocalDateTime deletedDate, String ISBN, String title, String description, LocalDate publishDate, String pageCount, String language, List<AuthorResponse> authors, List<String> categories, PublisherResponse publisher) {
        this.id = id;
        this.createdDate = createdDate;
        this.lastEditDate = lastEditDate;
        this.deletedDate = deletedDate;
        this.ISBN = ISBN;
        this.title = title;
        this.description = description;
        this.publishDate = publishDate;
        this.pageCount = pageCount;
        this.language = language;
        this.authors = authors;
        this.categories = categories;
        this.publisher = publisher;
    }
}
