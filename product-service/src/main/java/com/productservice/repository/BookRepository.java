package com.productservice.repository;

import com.productservice.document.Book;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BookRepository extends MongoRepository<Book, String> {
    List<Book> findByIsbnIn(List<String> isbnList);
}
