package com.productautofillservice.api.service;

import com.productautofillservice.request.GetIsbnListRequest;
import com.productautofillservice.request.IsbnDBListRequest;
import com.productautofillservice.response.GetDBIsbnListResponse;
import com.productautofillservice.response.GetIsbnListResponse;
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
    private final ProductService productService;

    public BookAutoAddService(OpenLibraryService openLibraryService, ProductService productService) {
        this.openLibraryService = openLibraryService;
        this.productService = productService;
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

            GetDBIsbnListResponse isbnDBList = productService.getDBPresentIsbnList(new IsbnDBListRequest(isbnList));
            isbnList.removeAll(isbnDBList.getIsbn());

            return GetIsbnListResponse.builder()
                    .numFound(isbnList.size())
                    .isbn(isbnList)
                    .build();
        }
        return null;
    }

}
