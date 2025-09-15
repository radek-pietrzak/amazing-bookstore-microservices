package com.productautofillservice.api.service;

import com.productautofillservice.request.IsbnDBListRequest;
import com.productautofillservice.response.GetDBIsbnListResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ProductService {

    private final RestTemplate restTemplate;

    public ProductService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public GetDBIsbnListResponse getDBPresentIsbnList(IsbnDBListRequest request) {
        //TODO to environment variable
        String apiUrl = "http://localhost:8082/product-service/isbn-list";
        ResponseEntity<GetDBIsbnListResponse> responseLibrary = restTemplate.postForEntity(apiUrl, request, GetDBIsbnListResponse.class);
        if (responseLibrary.getStatusCode().is2xxSuccessful()) {
            return responseLibrary.getBody();
        }
        return null;
    }
}
