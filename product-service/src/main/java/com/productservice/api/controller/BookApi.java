package com.productservice.api.controller;

import com.productservice.api.request.BookRequest;
import com.productservice.api.response.BookResponseList;
import com.productservice.api.response.Response;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.QueryParam;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

public interface BookApi {

    @GetMapping(path = API.GET_BOOK)
    ResponseEntity<?> getBook(@NotNull @PathVariable String id);

    @PostMapping(path = API.BOOK_SAVE)
    ResponseEntity<?> saveBook(@Valid @RequestBody BookRequest request, BindingResult bindingResult);

    @PostMapping(path = API.BOOK_EDIT)
    ResponseEntity<Response> editBook(
            @PathVariable String id,
            @Valid @RequestBody BookRequest request,
            HttpServletRequest servletRequest
    ) throws IllegalAccessException;

    @PutMapping(path = API.BOOK_DELETE)
    ResponseEntity<?> deleteBook(@NotNull @PathVariable String id);

    @GetMapping(path = API.BOOK_LIST)
    ResponseEntity<BookResponseList> getBookList(
            @Nullable @QueryParam(value = "search") String search,
            @Nullable @QueryParam(value = "page") Integer page,
            @Nullable @QueryParam(value = "pageSize") Integer pageSize);


}
