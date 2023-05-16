package com.productservice.api.controller;

import com.productservice.api.request.BookRequest;
import com.productservice.api.response.BadRequestResponse;
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
    // TODO generator of api responses
    @Override
    @Operation(
            description = "Get book",
            responses = {
                    @ApiResponse(responseCode = "200", ref = "successfullyGetBook"),
                    @ApiResponse(responseCode = "400", ref = "badBookRequestApi"),
                    @ApiResponse(responseCode = "500", ref = "internalErrorServerApi")
            }
    )
    public ResponseEntity<?> getBook(String id) {
        BookResponse response = bookService.getBook(id);
        if (response != null){
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.badRequest().body(BadRequestResponse.UNABLE_TO_FIND_BOOK + id);
    }

    private final ValidationService validationService;

    public BookController(BookService bookService, ValidationService validationService) {
        this.bookService = bookService;
        this.validationService = validationService;
    }

    @Override
    @Operation(
            description = "Save book",
            responses = {
                    @ApiResponse(responseCode = "202", ref = "successfullySavedBook"),
                    @ApiResponse(responseCode = "400", ref = "badBookRequestApi"),
                    @ApiResponse(responseCode = "500", ref = "internalErrorServerApi")
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
            response = "OK";
        }

        return ResponseEntity.ok().body(response);
    }

    @Override
    public ResponseEntity<?> editBook(String id, BookRequest request) {
        BookResponse response = bookService.editBook(id, request);
        return ResponseEntity.ok(response);
    }

    @Override
    @Operation(
            description = "Delete book",
            responses = {
                    @ApiResponse(responseCode = "200", ref = "successfullyDeletedBook"),
                    @ApiResponse(responseCode = "400", ref = "bookNotFound"),
                    @ApiResponse(responseCode = "500", ref = "internalErrorServerApi")
            }
    )
    public ResponseEntity<?> deleteBook(String id) {
        BookResponse response = bookService.deleteBook(id);
        if (response != null){
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.badRequest().body(BadRequestResponse.UNABLE_TO_FIND_BOOK + id);
    }

    //TODO list example in api response
    //TODO pageable infos
    @Override
    public ResponseEntity<BookResponseList> getBookList(String search, Integer page, Integer pageSize) {
        return ResponseEntity.ok(bookService.getBookList(search, page, pageSize));
    }
}
