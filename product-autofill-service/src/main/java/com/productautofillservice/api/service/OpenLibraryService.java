package com.productautofillservice.api.service;

import com.productautofillservice.api.controller.API;
import com.productautofillservice.response.OpenLibraryResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OpenLibraryService {

    private final RestTemplate restTemplate;

    public OpenLibraryService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public OpenLibraryResponse getBooks(String search) {
        String apiUrl = API.OPEN_LIBRARY_GET_BOOKS + "?q=" + search;
        ResponseEntity<OpenLibraryResponse> responseLibrary = restTemplate.getForEntity(apiUrl, OpenLibraryResponse.class);
        if (responseLibrary.getStatusCode().is2xxSuccessful()) {
            return responseLibrary.getBody();
        }
        return null;
    }
}
