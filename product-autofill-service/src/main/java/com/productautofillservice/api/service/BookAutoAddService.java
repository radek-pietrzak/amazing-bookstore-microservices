package com.productautofillservice.api.service;

import com.productautofillservice.ChatGPTHelper;
import com.productautofillservice.request.GetIsbnListRequest;
import com.productautofillservice.request.IsbnDBListRequest;
import com.productautofillservice.request.IsbnRequestList;
import com.productautofillservice.response.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
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
        BookDetailsResponseMapOpenLibrary bookDetails = openLibraryService.getOpenLibraryBookMapWithDetails(isbn);

        if (Objects.nonNull(bookDetails)) {

            List<BookDetailsResponse> bookDetailsResponseList = new ArrayList<>();


            bookDetails.getBookDetailsResponseOpenLibraryMap().forEach((key, value) -> {

                if (Objects.isNull(key) || Objects.isNull(value)) {
                    return;
                }

                BookDetailsResponseOpenLibrary.Details details = value.getDetails();

                if (Objects.isNull(details)) {
                    return;
                }

                String title = details.getTitle();

                if (Objects.isNull(title)) {
                    return;
                }

                List<String> authors = getAuthors(details);

                if (Objects.isNull(authors)) {
                    return;
                }

                Integer publishYear = getPublishYear(details);

                if (Objects.isNull(details.getPublish_date())) {
                    return;
                }

                int numberOfPages = details.getNumber_of_pages();

                if (numberOfPages < 1) {
                    return;
                }

                List<String> languages = getLanguages(details);

                if (Objects.isNull(languages)) {
                    return;
                }

                List<String> publishers = getPublishers(details);

                BookDetailsResponse bookDetailsResponse = BookDetailsResponse.builder()
                        .isbn(key)
                        .title(title)
                        .authors(authors)
                        .publishYear(publishYear)
                        .numberOfPages(numberOfPages)
                        .languages(languages)
                        .publishers(publishers)
                        .build();

                bookDetailsResponseList.add(bookDetailsResponse);
            });

            return BookDetailsResponseList.builder()
                    .numFound(bookDetailsResponseList.size())
                    .bookDetailsResponseList(bookDetailsResponseList)
                    .build();
        }

        return null;
    }

    private List<String> getAuthors(BookDetailsResponseOpenLibrary.Details details) {
        List<BookDetailsResponseOpenLibrary.Details.Author> authors = details.getAuthors();
        if (Objects.isNull(details.getAuthors())) {
            return null;
        }

        return authors.stream()
                .map(BookDetailsResponseOpenLibrary.Details.Author::getName)
                .collect(Collectors.toList());
    }

    private Integer getPublishYear(BookDetailsResponseOpenLibrary.Details details) {
        String publishDate = details.getPublish_date();
        if (Objects.isNull(publishDate)) {
            return null;
        }

        Pattern pattern = Pattern.compile("\\b\\d{4}\\b");
        Matcher matcher = pattern.matcher(publishDate);

        if (matcher.find()) {
            String yearString = matcher.group();
            return Integer.parseInt(yearString);
        }
        return null;
    }

    private List<String> getPublishers(BookDetailsResponseOpenLibrary.Details details) {
        List<String> publishers = details.getPublishers();
        if (Objects.isNull(publishers)) {
            return List.of();
        }

        return new ArrayList<>(publishers);
    }

    private List<String> getLanguages(BookDetailsResponseOpenLibrary.Details details) {
        List<BookDetailsResponseOpenLibrary.Details.Language> languages = details.getLanguages();
        String title = details.getTitle();

        if (Objects.isNull(languages)) {
            return null;
        }

        List<String> languagesStr = new ArrayList<>(languages.stream()
                .map(BookDetailsResponseOpenLibrary.Details.Language::getKey)
                .map(l -> l.replace("/languages/", ""))
                .toList());

        if (languages.isEmpty()) {
            languagesStr.add(getLanguagesFromChatGPT(title));
            return languagesStr;
        }

        return languagesStr;
    }

    private String getLanguagesFromChatGPT(String title) {
        try {
            ChatGPTHelper chatGPTHelper = new ChatGPTHelper();
            return chatGPTHelper.getLanguage(title);

        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

}
