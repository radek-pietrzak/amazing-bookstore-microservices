package com.productservice.api.controller;

import com.productservice.api.request.BookRequest;
import com.productservice.api.response.BookResponse;
import com.productservice.api.response.BookResponseList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController implements BookApi{

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public ResponseEntity<BookResponse> getBook(String id) {
        return ResponseEntity.ok(bookService.getBook(id));
    }

    @Override
    public ResponseEntity<HttpStatus> saveBook(BookRequest request) {
        bookService.saveBook(request);
        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<HttpStatus> editBook(String id, BookRequest request) {
        bookService.editBook(id, request);
        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<HttpStatus> deleteBook(String id) {
        bookService.deleteBook(id);
        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<BookResponseList> getBookList(String search) {
        return ResponseEntity.ok(bookService.getBookList(search));
    }
}
