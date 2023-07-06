package com.productautofillservice.api.controller;
import com.productautofillservice.request.GetIsbnListRequest;
import com.productautofillservice.request.IsbnRequestList;
import com.productautofillservice.response.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface BookAutoAddApi {
    @PostMapping(API.ISBN_LIST)
    ResponseEntity<Response> getIsbnList(@RequestBody GetIsbnListRequest request);

    @PostMapping(API.OPEN_LIBRARY_BOOK_DETAILS_LIST)
    ResponseEntity<Response> getOpenLibraryBookDetails(IsbnRequestList isbn);
}
