package com.productservice.api.controller;

import com.productservice.api.request.BookRequest;
import com.productservice.api.response.BookResponse;
import com.productservice.api.response.BookResponseList;
import org.springframework.stereotype.Service;

@Service
public class BookService {


    public BookResponse getBook(String id) {
        return null;
    }

    public void saveBook(BookRequest request) {
    }

    public void editBook(String id, BookRequest request) {
    }

    public void deleteBook(String id) {
    }

    public BookResponseList getBookList(String search) {
        return null;
    }
}
