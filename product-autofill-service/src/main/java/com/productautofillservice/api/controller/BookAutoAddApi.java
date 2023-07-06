package com.productautofillservice.api.controller;
import com.productautofillservice.request.GetIsbnListRequest;
import com.productautofillservice.request.IsbnDBListRequest;
import com.productautofillservice.response.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface BookAutoAddApi {
    @PostMapping(API.OPEN_LIBRARY_ISBN_LIST)
    ResponseEntity<Response> getIsbnList(@RequestBody GetIsbnListRequest request);

    @PostMapping(API.PRODUCT_SERVICE_ISBN_LIST)
    ResponseEntity<Response> getDBPresentIsbnList(@RequestBody IsbnDBListRequest request);

}
