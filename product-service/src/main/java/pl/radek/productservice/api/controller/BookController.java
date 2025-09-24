package pl.radek.productservice.api.controller;

import pl.radek.productservice.api.criteria.BookListCriteria;
import pl.radek.productservice.api.criteria.SearchSortKey;
import pl.radek.productservice.api.criteria.Sort;
import pl.radek.productservice.api.request.AutoFillBookListRequest;
import pl.radek.productservice.api.request.BookRequest;
import pl.radek.productservice.api.request.IsbnListRequest;
import pl.radek.productservice.api.response.*;
import pl.radek.productservice.api.response.Response;
import pl.radek.productservice.api.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class BookController implements BookApi {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    @Operation(
            description = "Get book",
            responses = {
                    @ApiResponse(responseCode = "200", ref = "successfullyGetBook"),
                    @ApiResponse(responseCode = "404", ref = "notFoundExceptionGet")
            }
    )
    public ResponseEntity<Response> getBook(String id) {
        Response response = bookService.getBook(id);
        return ResponseEntity.ok(response);
    }

    @Override
    @Operation(
            description = "Save book",
            responses = {
                    @ApiResponse(responseCode = "202", ref = "successfullySavedBook"),
                    @ApiResponse(responseCode = "400", ref = "validationExceptionSave")
            }
    )
    public ResponseEntity<Response> saveBook(BookRequest request) {
        Response response = bookService.saveBook(request);
        return ResponseEntity.ok().body(response);
    }

    @Operation(
            description = "Edit book",
            responses = {
                    @ApiResponse(responseCode = "200", ref = "successfullyEditedBook"),
                    @ApiResponse(responseCode = "400", ref = "validationExceptionEdit"),
                    @ApiResponse(responseCode = "404", ref = "notFoundExceptionEdit")
            }
    )
    @Override
    public ResponseEntity<Response> editBook(String id, BookRequest request) throws IllegalAccessException {
        Response response = bookService.editBook(id, request);
        return ResponseEntity.ok(response);
    }

    @Override
    @Operation(
            description = "Delete book",
            responses = {
                    @ApiResponse(responseCode = "200", ref = "successfullyDeletedBook"),
                    @ApiResponse(responseCode = "404", ref = "notFoundExceptionDelete")
            }
    )
    public ResponseEntity<Response> deleteBook(String id) {
        Response response = bookService.deleteBook(id);
        return ResponseEntity.ok(response);
    }

    //TODO no booklist in response body
    //TODO check all parameters and fix bugs
    @Override
    @Operation(
            description = "Book list",
            responses = {
                    @ApiResponse(responseCode = "200", ref = "successfullyGetBookList")
            }
    )
    public ResponseEntity<Response> getBookList(String search, Integer page, Integer pageSize, SearchSortKey searchKey, SearchSortKey sortKey, Sort sort) {
        BookListCriteria bookListCriteria = BookListCriteria.builder()
                .search(search)
                .pageNo(page)
                .pageSize(pageSize)
                .searchKey(searchKey)
                .sortKey(sortKey)
                .sort(sort)
                .build();
        return ResponseEntity.ok(bookService.getBookList(bookListCriteria));
    }

    //TODO openapi
    public ResponseEntity<Response> getIsbnList(IsbnListRequest isbnList) {
        Response response = bookService.getIsbnList(isbnList.getIsbn());
        return ResponseEntity.ok(response);
    }

    //TODO implement this functionality
    @Override
    public ResponseEntity<Response> saveBookList(AutoFillBookListRequest request) {
        Response response = bookService.saveBookList(request);
        return ResponseEntity.ok(response);
    }

    // TODO create response body in case inventory service not available
    @Override
    public ResponseEntity<Response> getInventoryByIsbn(String isbn) {
        Response  response = bookService.getInventoryByIsbn(isbn);
        return ResponseEntity.ok(response);
    }


}
