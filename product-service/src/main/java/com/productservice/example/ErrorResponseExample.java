package com.productservice.example;

import com.productservice.api.response.ErrorResponse;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface ErrorResponseExample {
    ErrorResponse VALIDATION_EXCEPTION = ErrorResponse.builder()
            .timestamp(LocalDateTime.now())
            .status(400)
            .path("/product-service/book/edit/1")
            .exception(null)
            .validationMessages(List.of(
                    "Invalid ISBN",
                    "Invalid Category",
                    "Invalid language code"))
            .build();

    ErrorResponse NOT_FOUND_EXCEPTION = ErrorResponse.builder()
            .timestamp(LocalDateTime.now())
            .status(404)
            .error(HttpStatus.NOT_FOUND)
            .path("/product-service/book/edit/1")
            .exception("java.util.NoSuchElementException")
            .validationMessages(null)
            .build();
}
