package com.productservice.api.controller;

import com.productservice.api.request.BookRequest;
import com.productservice.api.response.BookResponse;
import com.productservice.api.response.BookResponseList;
import com.productservice.api.service.BookService;
import com.productservice.api.service.ValidationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class BookController implements BookApi{

    private final BookService bookService;
    private final ValidationService validationService;

    public BookController(BookService bookService, ValidationService validationService) {
        this.bookService = bookService;
        this.validationService = validationService;
    }

    @Override
    public ResponseEntity<BookResponse> getBook(String id) {
        return ResponseEntity.ok(bookService.getBook(id));
    }

    @Override
    public ResponseEntity<?> saveBook(@Valid @RequestBody BookRequest bookRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(validationService.errorMessages(bindingResult));
        }
        bookService.saveBook(bookRequest);
        return ResponseEntity.accepted().build();
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
