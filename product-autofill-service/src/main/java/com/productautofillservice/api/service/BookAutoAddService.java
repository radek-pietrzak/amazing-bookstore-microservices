package com.productautofillservice.api.service;

import com.productautofillservice.request.GetBooksRequest;
import com.productautofillservice.response.GetBooksResponse;
import com.productautofillservice.response.OpenLibraryResponse;
import com.productautofillservice.response.Response;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class BookAutoAddService {

    private final OpenLibraryService openLibraryService;

    public BookAutoAddService(OpenLibraryService openLibraryService) {
        this.openLibraryService = openLibraryService;
    }

    public Response getBooks(GetBooksRequest request) {
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

            return GetBooksResponse.builder()
                    .numFound(isbnList.size())
                    .isbn(isbnList)
                    .build();
        }
        return null;
    }
}
