package com.productautofillservice.api.service;

import com.productautofillservice.api.controller.API;
import com.productautofillservice.response.BookDetailsResponse;
import com.productautofillservice.response.OpenLibraryResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

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

    public BookDetailsResponse getBookDetails(String isbn) {
        String apiUrl = "https://openlibrary.org/api/books?bibkeys=ISBN:" + isbn + "&jscmd=details&format=json";
        ResponseEntity<Map<String, BookDetailsResponse>> responseLibrary = restTemplate.exchange(apiUrl, HttpMethod.GET, null, new ParameterizedTypeReference<>() {});
        if (responseLibrary.getStatusCode().is2xxSuccessful()) {
            Map<String, BookDetailsResponse> responseMap = responseLibrary.getBody();
            if (responseMap != null && !responseMap.isEmpty()) {
                // Wyszukiwanie pierwszego klucza w mapie odpowiedzi (ISBN jako klucz)
                String firstKey = responseMap.keySet().iterator().next();
                return responseMap.get(firstKey);
            }
        }
        return null;
    }

}
