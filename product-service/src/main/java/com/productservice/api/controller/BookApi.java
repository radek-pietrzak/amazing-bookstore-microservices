package com.productservice.api.controller;

import com.productservice.api.request.BookRequest;
import com.productservice.api.response.BookResponse;
import com.productservice.api.response.BookResponseList;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.QueryParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface BookApi {

    @GetMapping(path = API.BOOK)
    ResponseEntity<BookResponse> getBook(@NotNull @PathVariable String id);

    @PostMapping(path = API.BOOK_SAVE)
    ResponseEntity<HttpStatus> saveBook(@Valid @RequestBody BookRequest request);

    @PostMapping(path = API.BOOK_EDIT)
    ResponseEntity<HttpStatus> editBook(@NotNull @PathVariable String id, @Valid @RequestBody BookRequest request);

    @PutMapping(path = API.BOOK_DELETE)
    ResponseEntity<HttpStatus> deleteBook(@NotNull @PathVariable String id);

    @GetMapping(path = API.BOOK_LIST)
    ResponseEntity<BookResponseList> getBookList(@QueryParam(value = "search") String search);


}