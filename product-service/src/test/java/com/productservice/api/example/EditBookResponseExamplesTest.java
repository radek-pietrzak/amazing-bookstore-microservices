package com.productservice.api.example;

import com.productservice.api.response.BookResponse;
import com.productservice.api.response.EditBookResponse;

public class EditBookResponseExamplesTest {

    public static EditBookResponse getEditBookResponse(boolean modified, BookResponse bookResponse) {
        return EditBookResponse.builder()
                .modified(modified)
                .id(bookResponse.getId())
                .createdDate(bookResponse.getCreatedDate())
                .lastEditDate(bookResponse.getLastEditDate())
                .deletedDate(bookResponse.getDeletedDate())
                .isbn(bookResponse.getIsbn())
                .title(bookResponse.getTitle())
                .authors(bookResponse.getAuthors())
                .description(bookResponse.getDescription())
                .categories(bookResponse.getCategories())
                .publisher(bookResponse.getPublisher())
                .publishYear(bookResponse.getPublishYear())
                .pageCount(bookResponse.getPageCount())
                .languageCode(bookResponse.getLanguageCode())
                .build();
    }

}
