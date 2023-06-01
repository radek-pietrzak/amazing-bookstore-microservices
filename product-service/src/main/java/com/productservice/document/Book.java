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
}
