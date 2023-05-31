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
@Setter
@Builder
@EqualsAndHashCode
public class Book {

    @Id
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
    private Integer pageCount;
    private String languageCode;

    public static BookBuilder builder(Book book) {
        BookBuilder builder = new BookBuilder();
        builder.id = book.id;
        builder.createdDate = book.createdDate;
        builder.lastEditDate = book.lastEditDate;
        builder.deletedDate = book.deletedDate;
        builder.isbn = book.isbn;
        builder.title = book.title;
        builder.authors = book.authors;
        builder.description = book.description;
        builder.categories = book.categories;
        builder.publisher = book.publisher;
        builder.publishYear = book.publishYear;
        builder.pageCount = book.pageCount;
        builder.languageCode = book.languageCode;
        return builder;
    }

    public static BookBuilder builder() {
        return new BookBuilder();
    }
}
