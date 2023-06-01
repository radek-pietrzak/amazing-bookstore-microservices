package com.productservice.example;

import com.productservice.api.controller.API;
import com.productservice.api.response.ErrorResponse;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface ErrorResponseExample {
    ErrorResponse VALIDATION_EXCEPTION_EDIT = ErrorResponse.builder()
            .timestamp(LocalDateTime.now())
            .status(400)
            .path(API.BOOK_EDIT + "1")
            .exception(null)
            .validationMessages(List.of(
                    "Invalid ISBN",
                    "Invalid Category",
                    "Invalid language code"))
            .build();

    ErrorResponse VALIDATION_EXCEPTION_SAVE = ErrorResponse.builder()
            .timestamp(LocalDateTime.now())
            .status(400)
            .path(API.BOOK_SAVE)
            .exception(null)
            .validationMessages(List.of(
                    "Invalid ISBN",
                    "Invalid Category",
                    "Invalid language code"))
            .build();

    ErrorResponse NOT_FOUND_EXCEPTION_EDIT = ErrorResponse.builder()
            .timestamp(LocalDateTime.now())
            .status(404)
            .error(HttpStatus.NOT_FOUND)
            .path(API.BOOK_EDIT + "1")
            .exception("java.util.NoSuchElementException")
            .validationMessages(null)
            .build();

    ErrorResponse NOT_FOUND_EXCEPTION_GET = ErrorResponse.builder()
            .timestamp(LocalDateTime.now())
            .status(404)
            .error(HttpStatus.NOT_FOUND)
            .path(API.BOOK_GET + "1")
            .exception("java.util.NoSuchElementException")
            .validationMessages(null)
            .build();

    ErrorResponse NOT_FOUND_EXCEPTION_DELETE = ErrorResponse.builder()
            .timestamp(LocalDateTime.now())
            .status(404)
            .error(HttpStatus.NOT_FOUND)
            .path(API.BOOK_DELETE + "1")
            .exception("java.util.NoSuchElementException")
            .validationMessages(null)
            .build();
}
