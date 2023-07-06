package com.productautofillservice.api.service;

import com.productautofillservice.request.GetIsbnListRequest;
import com.productautofillservice.request.IsbnDBListRequest;
import com.productautofillservice.response.GetDBIsbnListResponse;
import com.productautofillservice.response.GetIsbnListResponse;
import com.productautofillservice.response.OpenLibraryResponse;
import com.productautofillservice.response.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class BookAutoAddService {

    private final OpenLibraryService openLibraryService;
    private final RestTemplate restTemplate;

    public BookAutoAddService(OpenLibraryService openLibraryService, RestTemplate restTemplate) {
        this.openLibraryService = openLibraryService;
        this.restTemplate = restTemplate;
    }

    public Response getIsbnList(GetIsbnListRequest request) {
        String search = request.getSearchRequest().getGeneral();
        search = search.replace(" ", "+");
        OpenLibraryResponse openLibraryResponse = openLibraryService.getBooks(search);
        if (Objects.nonNull(openLibraryResponse)) {

            List<String> isbnList = openLibraryResponse.getDocs().stream()
                    .filter(doc -> Objects.nonNull(doc.getIsbn()))
                    .flatMap(doc -> Optional.of(doc.getIsbn()).orElse(List.of()).stream()
                            .filter(isbn -> Pattern.matches("\\d{13}", isbn)))
                    .distinct()
                    .collect(Collectors.toList());

            return GetIsbnListResponse.builder()
                    .numFound(isbnList.size())
                    .isbn(isbnList)
                    .build();
        }
        return null;
    }

    public Response getDBPresentIsbnList(IsbnDBListRequest request) {
        //TODO to environment variable
        String apiUrl = "http://localhost:8082/product-service/isbn-list";
        ResponseEntity<GetDBIsbnListResponse> responseLibrary = restTemplate.postForEntity(apiUrl, request, GetDBIsbnListResponse.class);
        if (responseLibrary.getStatusCode().is2xxSuccessful()) {
            return responseLibrary.getBody();
        }
        return null;
    }
}
