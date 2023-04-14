package com.productservice.api.request;

import java.time.LocalDate;
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
    private LocalDate publishDate;
    private String pageCount;
    private String language;
    private List<AuthorRequest> authors;
    private List<String> categories;
    private PublisherRequest publisher;

    private BookRequest(BookRequestBuilder builder) {
        this.id = builder.id;
        this.createdDate = builder.createdDate;
        this.lastEditDate = builder.lastEditDate;
        this.deletedDate = builder.deletedDate;
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

    public String getId() {
        return id;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public LocalDateTime getLastEditDate() {
        return lastEditDate;
    }

    public LocalDateTime getDeletedDate() {
        return deletedDate;
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
        private String id;
        private LocalDateTime createdDate;
        private LocalDateTime lastEditDate;
        private LocalDateTime deletedDate;
        private String ISBN;
        private String title;
        private String description;
        private LocalDate publishDate;
        private String pageCount;
        private String language;
        private List<AuthorRequest> authors;
        private List<String> categories;
        private PublisherRequest publisher;

        public BookRequestBuilder id(String id) {
            this.id = id;
            return this;
        }

        public BookRequestBuilder createdDate(LocalDateTime createdDate) {
            this.createdDate = createdDate;
            return this;
        }

        public BookRequestBuilder lastEditDate(LocalDateTime lastEditDate) {
            this.lastEditDate = lastEditDate;
            return this;
        }

        public BookRequestBuilder deletedDate(LocalDateTime deletedDate) {
            this.deletedDate = deletedDate;
            return this;
        }

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
