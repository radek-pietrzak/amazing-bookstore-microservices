package com.productautofillservice.api.service;

import com.productautofillservice.ChatGPTHelper;
import com.productautofillservice.request.GetIsbnListRequest;
import com.productautofillservice.request.IsbnDBListRequest;
import com.productautofillservice.request.IsbnRequestList;
import com.productautofillservice.response.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
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
            if (Objects.nonNull(isbnDBList.getIsbn())) {
                isbnList.removeAll(isbnDBList.getIsbn());
            }

            return GetIsbnListResponse.builder()
                    .numFound(isbnList.size())
                    .isbn(isbnList)
                    .build();
        }
        return null;
    }


    public Response getBookListWithDetails(IsbnRequestList isbn) {
        //TODO mapstruct
        BookDetailsResponseMapOpenLibrary bookDetails = openLibraryService.getOpenLibraryBookMapWithDetails(isbn);

        if (Objects.nonNull(bookDetails)) {
            List<BookDetailsResponse> bookDetailsResponseList = bookDetails.getBookDetailsResponseOpenLibraryMap().entrySet().stream()
                    .map(entry -> BookDetailsResponse.builder()
                            .isbn(entry.getKey())
                            .title(entry.getValue().getDetails().getTitle())
                            .authors(getAuthors(entry.getValue()))
                            .publishDate(entry.getValue().getDetails().getPublish_date())
                            .publishers(getPublishers(entry.getValue()))
                            .numberOfPages(entry.getValue().getDetails().getNumber_of_pages())
                            .languages(getLanguages(entry.getValue()))
                            .build())
                    .toList();


            return BookDetailsResponseList.builder()
                    .numFound(bookDetails.getNumFound())
                    .bookDetailsResponseList(bookDetailsResponseList)
                    .build();
        }

        return null;
    }

    private List<String> getAuthors(BookDetailsResponseOpenLibrary bookDetails) {
        if (Objects.isNull(bookDetails) || Objects.isNull(bookDetails.getDetails()) || Objects.isNull(bookDetails.getDetails().getAuthors())) {
            return List.of();
        }

        return bookDetails.getDetails().getAuthors().stream()
                .map(BookDetailsResponseOpenLibrary.Details.Author::getName)
                .collect(Collectors.toList());
    }

    private List<String> getPublishers(BookDetailsResponseOpenLibrary bookDetails) {
        if (Objects.isNull(bookDetails) || Objects.isNull(bookDetails.getDetails()) || Objects.isNull(bookDetails.getDetails().getPublishers())) {
            return List.of();
        }

        return new ArrayList<>(bookDetails.getDetails().getPublishers());
    }

    private List<String> getLanguages(BookDetailsResponseOpenLibrary bookDetails) {
        List<String> languages = new ArrayList<>();
        String title;

        if (!Objects.isNull(bookDetails) && !Objects.isNull(bookDetails.getDetails())) {
            title = bookDetails.getDetails().getTitle();

            if (Objects.isNull(bookDetails.getDetails().getLanguages())) {
                languages.add(getLanguagesFromChatGPT(title));
                return languages;
            }

            languages = bookDetails.getDetails().getLanguages().stream()
                    .map(BookDetailsResponseOpenLibrary.Details.Language::getKey)
                    .map(l -> l.replace("/languages/", ""))
                    .toList();

            if (languages.isEmpty()) {
                getLanguagesFromChatGPT(title);
                return languages;
            }
        }

        return languages;
    }

    private String getLanguagesFromChatGPT(String title) {
        try {
            ChatGPTHelper chatGPTHelper = new ChatGPTHelper();
            return chatGPTHelper.getLanguage(title);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

}
