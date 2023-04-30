package com.productservice.api.controller;

import com.productservice.api.request.BookRequest;
import com.productservice.api.response.BookResponse;
import com.productservice.api.response.BookResponseList;
import com.productservice.api.service.BookService;
import com.productservice.api.service.ValidationService;
import com.productservice.api.util.JsonFileToJsonObject;
import com.productservice.examples.BookRequestJsonExample;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping
public class BookController implements BookApi {

    private final BookService bookService;
    private final ValidationService validationService;

    public BookController(BookService bookService, ValidationService validationService) {
        this.bookService = bookService;
        this.validationService = validationService;
    }

    @Override
    public ResponseEntity<BookResponse> getBook(String id) {
        return ResponseEntity.ok(bookService.getBook(id));
    }

    @Override
    @Operation(
            description = "Save book",
            responses = {
                    @ApiResponse(responseCode = "400", ref = "badBookRequestApi"),
                    @ApiResponse(responseCode = "500", ref = "internalErrorServerApi"),
                    @ApiResponse(responseCode = "202", ref = "successfullySavedBook")
            }
    )
    public ResponseEntity<?> saveBook(@RequestBody(
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = {
                            @ExampleObject(value = BookRequestJsonExample.VALID_BOOK_1)
                    }
            )
    ) BookRequest bookRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(validationService.errorMessages(bindingResult));
        }
        bookService.saveBook(bookRequest);
        String response;

        try {
            JsonFileToJsonObject jsonFileToJsonObject = new JsonFileToJsonObject();
            response = jsonFileToJsonObject.readByFileName("response").get("successfullySavedBook").toString();
        } catch (IOException e) {
            response = "ACCEPTED";
        }

        return ResponseEntity.accepted().body(response);
    }

    @Override
    public ResponseEntity<HttpStatus> editBook(String id, BookRequest request) {
        bookService.editBook(id, request);
        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<HttpStatus> deleteBook(String id) {
        bookService.deleteBook(id);
        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<BookResponseList> getBookList(String search) {
        // TODO search from parameter
        return ResponseEntity.ok(bookService.getBookList(search));
    }
}
