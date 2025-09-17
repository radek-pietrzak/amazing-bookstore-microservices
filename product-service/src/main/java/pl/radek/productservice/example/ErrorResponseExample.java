package pl.radek.productservice.example;

import pl.radek.productservice.api.controller.API;
import pl.radek.productservice.api.response.ErrorResponse;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

public abstract class ErrorResponseExample {

    public static ErrorResponse getValidationExceptionEdit(){
        return ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(400)
                .path(API.BOOK_EDIT + "1")
                .exception(null)
                .validationMessages(List.of(
                        "Invalid ISBN",
                        "Invalid Category",
                        "Invalid language code"))
                .build();
    }

    public static ErrorResponse getValidationExceptionSave() {
        return ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(400)
                .path(API.BOOK_SAVE)
                .exception(null)
                .validationMessages(List.of(
                        "Invalid ISBN",
                        "Invalid Category",
                        "Invalid language code"))
                .build();
    }

    public static ErrorResponse getNotFoundExceptionEdit() {
        return ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(404)
                .error(HttpStatus.NOT_FOUND)
                .path(API.BOOK_EDIT + "1")
                .exception("java.util.NoSuchElementException")
                .validationMessages(null)
                .build();
    }

    public static ErrorResponse getNotFoundExceptionGet() {
        return ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(404)
                .error(HttpStatus.NOT_FOUND)
                .path(API.BOOK_GET + "1")
                .exception("java.util.NoSuchElementException")
                .validationMessages(null)
                .build();
    }

    public static ErrorResponse getNotFoundExceptionDelete() {
        return ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(404)
                .error(HttpStatus.NOT_FOUND)
                .path(API.BOOK_DELETE + "1")
                .exception("java.util.NoSuchElementException")
                .validationMessages(null)
                .build();
    }

}
