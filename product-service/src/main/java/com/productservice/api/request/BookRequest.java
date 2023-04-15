package com.productservice.api.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public class BookRequest {
    @NotNull(message = "ISBN cannot be null")
    private String ISBN;
    @NotNull(message = "Title cannot be null")
    private String title;
    @NotNull(message = "Description cannot be null")
    private String description;
    @NotNull(message = "Publish Date cannot be null")
    private LocalDate publishDate;
    @NotNull(message = "Page Count cannot be null")
    private String pageCount;
    @NotNull(message = "Language cannot be null")
    private String language;
    @NotNull(message = "Authors cannot be null")
    @Valid
    private List<AuthorRequest> authors;
    @NotNull(message = "Categories cannot be null")
    private List<String> categories;
    @NotNull(message = "Publisher cannot be null")
    @Valid
    private PublisherRequest publisher;

    public BookRequest() {
    }

    private BookRequest(BookRequestBuilder builder) {
        this.ISBN = builder.ISBN;
        this.title = builder.title;
        this.description = builder.description;
        this.publishDate = builder.publishDate;
        this.pageCount = builder.pageCount;
        this.language = builder.language;
        this.authors = builder.authors;
        this.categories = builder.categories;
        this.publisher = builder.publisher;
    }

    public String getISBN() {
        return ISBN;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getPublishDate() {
        return publishDate;
    }

    public String getPageCount() {
        return pageCount;
    }

    public String getLanguage() {
        return language;
    }

    public List<AuthorRequest> getAuthors() {
        return authors;
    }

    public List<String> getCategories() {
        return categories;
    }

    public PublisherRequest getPublisher() {
        return publisher;
    }

    public static BookRequestBuilder builder() {
        return new BookRequestBuilder();
    }

    public static class BookRequestBuilder {
        private String ISBN;
        private String title;
        private String description;
        private LocalDate publishDate;
        private String pageCount;
        private String language;
        private List<AuthorRequest> authors;
        private List<String> categories;
        private PublisherRequest publisher;

        public BookRequestBuilder ISBN(String ISBN) {
            this.ISBN = ISBN;
            return this;
        }

        public BookRequestBuilder title(String title) {
            this.title = title;
            return this;
        }

        public BookRequestBuilder description(String description) {
            this.description = description;
            return this;
        }

        public BookRequestBuilder publishDate(LocalDate publishDate) {
            this.publishDate = publishDate;
            return this;
        }

        public BookRequestBuilder pageCount(String pageCount) {
            this.pageCount = pageCount;
            return this;
        }

        public BookRequestBuilder language(String language) {
            this.language = language;
            return this;
        }

        public BookRequestBuilder authors(List<AuthorRequest> authors) {
            this.authors = authors;
            return this;
        }

        public BookRequestBuilder categories(List<String> categories) {
            this.categories = categories;
            return this;
        }

        public BookRequestBuilder publisher(PublisherRequest publisher) {
            this.publisher = publisher;
            return this;
        }

        public BookRequest build() {
            return new BookRequest(this);
        }
    }
}
