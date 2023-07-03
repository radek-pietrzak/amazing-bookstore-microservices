package com.productautofillservice.api.controller;
import com.productautofillservice.request.GetBooksRequest;
import com.productautofillservice.response.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface BookAutoAddApi {
    @PostMapping(API.GET_BOOKS)
    ResponseEntity<Response> getBooks(@RequestBody GetBooksRequest request);
}
