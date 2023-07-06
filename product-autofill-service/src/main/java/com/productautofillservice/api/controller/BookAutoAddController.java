package com.productautofillservice.api.controller;

import com.productautofillservice.api.service.BookAutoAddService;
import com.productautofillservice.request.GetIsbnListRequest;
import com.productautofillservice.response.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class BookAutoAddController implements BookAutoAddApi {
    private final BookAutoAddService bookAutoAddService;

    public BookAutoAddController(BookAutoAddService bookAutoAddService) {
        this.bookAutoAddService = bookAutoAddService;
    }

    @Override
    public ResponseEntity<Response> getIsbnList(GetIsbnListRequest request) {
        Response response = bookAutoAddService.getIsbnList(request);
        return ResponseEntity.ok(response);
    }
}
