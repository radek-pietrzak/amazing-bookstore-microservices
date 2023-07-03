package com.productautofillservice.api.service;

import com.productautofillservice.request.GetBooksRequest;
import com.productautofillservice.response.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class BookAutoAddService {
    public ResponseEntity<Response> getBooks(GetBooksRequest request) {
        return null;
    }
}
