package com.productautofillservice.api.service;

import com.productautofillservice.request.GetBooksRequest;
import com.productautofillservice.response.GetBooksResponse;
import com.productautofillservice.response.OpenLibraryDoc;
import com.productautofillservice.response.OpenLibraryResponse;
import com.productautofillservice.response.Response;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

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
            List<String> titles = openLibraryResponse.getDocs().stream()
                    .map(OpenLibraryDoc::getTitle)
                    .toList();

            return GetBooksResponse.builder()
                    .numFound(openLibraryResponse.getNumFound())
                    .titles(titles)
                    .build();
        }
        return null;
    }
}
