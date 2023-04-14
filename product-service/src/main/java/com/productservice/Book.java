package com.productservice;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

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

    private Book(BookBuilder bookBuilder) {
        this.createdDate = bookBuilder.createdDate;
        this.lastEditDate = bookBuilder.lastEditDate;
        this.deletedDate = bookBuilder.deletedDate;
        this.ISBN = bookBuilder.ISBN;
        this.title = bookBuilder.title;
        this.authors = bookBuilder.authors;
        this.description = bookBuilder.description;
        this.categories = bookBuilder.categories;
        this.publisher = bookBuilder.publisher;
        this.publishDate = bookBuilder.publishDate;
        this.pageCount = bookBuilder.pageCount;
        this.language = bookBuilder.language;
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

    public List<Author> getAuthors() {
        return authors;
    }

    public String getDescription() {
        return description;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public LocalDate getPublishDate() {
        return publishDate;
    }

    public int getPageCount() {
        return pageCount;
    }

    public String getLanguage() {
        return language;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book book)) return false;
        return getPageCount() == book.getPageCount() && Objects.equals(getISBN(), book.getISBN()) && Objects.equals(getTitle(), book.getTitle()) && Objects.equals(getAuthors(), book.getAuthors()) && Objects.equals(getDescription(), book.getDescription()) && Objects.equals(getCategories(), book.getCategories()) && Objects.equals(getPublisher(), book.getPublisher()) && Objects.equals(getPublishDate(), book.getPublishDate()) && Objects.equals(getLanguage(), book.getLanguage());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getISBN(), getTitle(), getAuthors(), getDescription(), getCategories(), getPublisher(), getPublishDate(), getPageCount(), getLanguage());
    }

    public static class BookBuilder {
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

        public BookBuilder() {
        }

        public BookBuilder(
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

        public BookBuilder createdDate(LocalDateTime createdDate) {
            this.createdDate = createdDate;
            return this;
        }

        public BookBuilder lastEditDate(LocalDateTime lastEditDate) {
            this.lastEditDate = lastEditDate;
            return this;
        }

        public BookBuilder deletedDate(LocalDateTime deletedDate) {
            this.deletedDate = deletedDate;
            return this;
        }

        public BookBuilder ISBN(String ISBN) {
            this.ISBN = ISBN;
            return this;
        }

        public BookBuilder title(String title) {
            this.title = title;
            return this;
        }

        public BookBuilder authors(List<Author> authors) {
            this.authors = authors;
            return this;
        }

        public BookBuilder description(String description) {
            this.description = description;
            return this;
        }

        public BookBuilder categories(List<Category> categories) {
            this.categories = categories;
            return this;
        }

        public BookBuilder publisher(Publisher publisher) {
            this.publisher = publisher;
            return this;
        }

        public BookBuilder publishDate(LocalDate publishDate) {
            this.publishDate = publishDate;
            return this;
        }

        public BookBuilder pageCount(int pageCount) {
            this.pageCount = pageCount;
            return this;
        }

        public BookBuilder language(String language) {
            this.language = language;
            return this;
        }

        public Book build() {
            return new Book(this);
        }
    }
}
