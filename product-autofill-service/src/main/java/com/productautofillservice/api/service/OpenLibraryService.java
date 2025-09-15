package com.productautofillservice.api.service;

import com.productautofillservice.api.controller.API;
import com.productautofillservice.request.IsbnRequestList;
import com.productautofillservice.response.BookDetailsResponseOpenLibrary;
import com.productautofillservice.response.BookDetailsResponseMapOpenLibrary;
import com.productautofillservice.response.OpenLibraryResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

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

    public BookDetailsResponseOpenLibrary getBookDetails(String isbn) {
        String apiUrl = "https://openlibrary.org/api/books?bibkeys=ISBN:" + isbn + "&jscmd=details&format=json";
        ResponseEntity<Map<String, BookDetailsResponseOpenLibrary>> responseLibrary = restTemplate.exchange(apiUrl, HttpMethod.GET, null, new ParameterizedTypeReference<>() {});
        if (responseLibrary.getStatusCode().is2xxSuccessful()) {
            Map<String, BookDetailsResponseOpenLibrary> responseMap = responseLibrary.getBody();
            if (responseMap != null && !responseMap.isEmpty()) {
                String firstKey = responseMap.keySet().iterator().next();
                return responseMap.get(firstKey);
            }
        }
        return null;
    }

    public BookDetailsResponseMapOpenLibrary getOpenLibraryBookMapWithDetails(IsbnRequestList isbn) {
        if (Objects.nonNull(isbn) && Objects.nonNull(isbn.getIsbn())) {
            Map<String, BookDetailsResponseOpenLibrary> bookDetailsMap = getIsbnOpenLibraryBookDetailsMap(isbn);

            return BookDetailsResponseMapOpenLibrary.builder()
                    .bookDetailsResponseOpenLibraryMap(bookDetailsMap)
                    .numFound(bookDetailsMap.size())
                    .build();
        }

        return null;
    }

    public Map<String, BookDetailsResponseOpenLibrary> getIsbnOpenLibraryBookDetailsMap(IsbnRequestList isbnList) {
        if (Objects.isNull(isbnList) || Objects.isNull(isbnList.getIsbn())) {
            return Collections.emptyMap();
        }

        return isbnList.getIsbn().stream()
                .collect(Collectors.toMap(
                        isbn -> isbn,
                        this::getBookDetails
                ));
    }

}
