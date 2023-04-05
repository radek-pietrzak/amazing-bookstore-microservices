package com.productservice;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document("books")
public class Book {

    @Id
    private String id;
    private Date createdDate;
    private Date lastEditDate;
    private Date deletedDate;
    private String ISBN;
    private String title;
    private List<Author> author;
    private String description;
    private List<Category> category;
    private Publisher publisher;
    private Date publishDate;
    private int pageCount;
    private String language;

}
