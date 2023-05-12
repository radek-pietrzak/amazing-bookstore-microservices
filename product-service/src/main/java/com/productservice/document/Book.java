package com.productservice.document;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document("books")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode(exclude = {"id", "createdDate", "lastEditDate", "deletedDate"})
public class Book {

    @Id
    private String id;
    @Setter
    private LocalDateTime createdDate;
    private LocalDateTime lastEditDate;
    @Setter
    private LocalDateTime deletedDate;
    private String isbn;
    private String title;
    private List<Author> authors;
    private String description;
    private List<Category> categories;
    private Publisher publisher;
    private Integer publishYear;
    private Integer pageCount;
    private String languageCode;

    private Book(BookBuilder bookBuilder) {
        this.id = bookBuilder.id;
        this.createdDate = bookBuilder.createdDate;
        this.lastEditDate = bookBuilder.lastEditDate;
        this.deletedDate = bookBuilder.deletedDate;
        this.isbn = bookBuilder.isbn;
        this.title = bookBuilder.title;
        this.authors = bookBuilder.authors;
        this.description = bookBuilder.description;
        this.categories = bookBuilder.categories;
        this.publisher = bookBuilder.publisher;
        this.publishYear = bookBuilder.publishYear;
        this.pageCount = bookBuilder.pageCount;
        this.languageCode = bookBuilder.languageCode;
    }

    public static BookBuilder builder(){
        return new BookBuilder();
    }

    public static class BookBuilder {
        private String id;
        private LocalDateTime createdDate;
        private LocalDateTime lastEditDate;
        private LocalDateTime deletedDate;
        private String isbn;
        private String title;
        private List<Author> authors;
        private String description;
        private List<Category> categories;
        private Publisher publisher;
        private Integer publishYear;
        private int pageCount;
        private String languageCode;

        public BookBuilder() {
        }

        public BookBuilder(Book book) {
            this.id = book.getId();
            this.createdDate = book.createdDate;
            this.lastEditDate = book.lastEditDate;
            this.deletedDate = book.deletedDate;
            this.isbn = book.isbn;
            this.title = book.title;
            this.authors = book.authors;
            this.description = book.description;
            this.categories = book.categories;
            this.publisher = book.publisher;
            this.publishYear = book.publishYear;
            this.pageCount = book.pageCount;
            this.languageCode = book.languageCode;
        }

        public BookBuilder id(String id) {
            this.id = id;
            return this;
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

        public BookBuilder isbn(String isbn) {
            this.isbn = isbn;
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

        public BookBuilder publishYear(Integer publishYear) {
            this.publishYear = publishYear;
            return this;
        }

        public BookBuilder pageCount(int pageCount) {
            this.pageCount = pageCount;
            return this;
        }

        public BookBuilder languageCode(String language) {
            this.languageCode = language;
            return this;
        }

        public Book build() {
            return new Book(this);
        }
    }
}
