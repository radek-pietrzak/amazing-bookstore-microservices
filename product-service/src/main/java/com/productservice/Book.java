package com.productservice;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Document("books")
public class Book {

    @Id
    private String id;
    private LocalDateTime createdDate;
    private LocalDateTime lastEditDate;
    private LocalDateTime deletedDate;
    private String ISBN;
    private String title;
    private List<Author> authors;
    private String description;
    private List<Category> categories;
    private Publisher publisher;
    private LocalDate publishDate;
    private int pageCount;
    private String language;

    public Book() {
    }

    private Book(
            LocalDateTime createdDate,
            LocalDateTime lastEditDate,
            LocalDateTime deletedDate,
            String ISBN,
            String title,
            List<Author> authors,
            String description,
            List<Category> categories,
            Publisher publisher,
            LocalDate publishDate,
            int pageCount,
            String language
    ) {
        this.createdDate = createdDate;
        this.lastEditDate = lastEditDate;
        this.deletedDate = deletedDate;
        this.ISBN = ISBN;
        this.title = title;
        this.authors = authors;
        this.description = description;
        this.categories = categories;
        this.publisher = publisher;
        this.publishDate = publishDate;
        this.pageCount = pageCount;
        this.language = language;
    }

    private Book(Builder builder) {
        this.createdDate = builder.createdDate;
        this.lastEditDate = builder.lastEditDate;
        this.deletedDate = builder.deletedDate;
        this.ISBN = builder.ISBN;
        this.title = builder.title;
        this.authors = builder.authors;
        this.description = builder.description;
        this.categories = builder.categories;
        this.publisher = builder.publisher;
        this.publishDate = builder.publishDate;
        this.pageCount = builder.pageCount;
        this.language = builder.language;
    }

    public static class Builder {
        private LocalDateTime createdDate;
        private LocalDateTime lastEditDate;
        private LocalDateTime deletedDate;
        private String ISBN;
        private String title;
        private List<Author> authors;
        private String description;
        private List<Category> categories;
        private Publisher publisher;
        private LocalDate publishDate;
        private int pageCount;
        private String language;

        public Builder() {
        }

        public Builder(
                LocalDateTime createdDate,
                LocalDateTime lastEditDate,
                LocalDateTime deletedDate,
                String ISBN, String title,
                List<Author> authors,
                String description,
                List<Category> categories,
                Publisher publisher,
                LocalDate publishDate,
                int pageCount,
                String language
        ) {
            this.createdDate = createdDate;
            this.lastEditDate = lastEditDate;
            this.deletedDate = deletedDate;
            this.ISBN = ISBN;
            this.title = title;
            this.authors = authors;
            this.description = description;
            this.categories = categories;
            this.publisher = publisher;
            this.publishDate = publishDate;
            this.pageCount = pageCount;
            this.language = language;
        }

        public Builder createdDate(LocalDateTime createdDate) {
            this.createdDate = createdDate;
            return this;
        }

        public Builder lastEditDate(LocalDateTime lastEditDate) {
            this.lastEditDate = lastEditDate;
            return this;
        }

        public Builder deletedDate(LocalDateTime deletedDate) {
            this.deletedDate = deletedDate;
            return this;
        }

        public Builder ISBN(String ISBN) {
            this.ISBN = ISBN;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder authors(List<Author> authors) {
            this.authors = authors;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder categories(List<Category> categories) {
            this.categories = categories;
            return this;
        }

        public Builder publisher(Publisher publisher) {
            this.publisher = publisher;
            return this;
        }

        public Builder publishDate(LocalDate publishDate) {
            this.publishDate = publishDate;
            return this;
        }

        public Builder pageCount(int pageCount) {
            this.pageCount = pageCount;
            return this;
        }

        public Builder language(String language) {
            this.language = language;
            return this;
        }

        public Book build() {
            return new Book(this);
        }
    }
}
