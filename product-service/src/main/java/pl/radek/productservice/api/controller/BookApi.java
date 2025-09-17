package pl.radek.productservice.api.controller;

import pl.radek.productservice.api.request.AutoFillBookListRequest;
import pl.radek.productservice.api.request.BookRequest;
import pl.radek.productservice.api.request.IsbnListRequest;
import pl.radek.productservice.api.response.Response;
import pl.radek.productservice.api.criteria.SearchSortKey;
import pl.radek.productservice.api.criteria.Sort;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface BookApi {

    @GetMapping(path = API.BOOK_GET_ID)
    ResponseEntity<Response> getBook(@PathVariable String id);

    @PostMapping(path = API.BOOK_SAVE)
    ResponseEntity<Response> saveBook(@Valid @RequestBody BookRequest request);

    //TODO change to put
    @PostMapping(path = API.BOOK_EDIT_ID)
    ResponseEntity<Response> editBook(@PathVariable String id, @Valid @RequestBody BookRequest request)
            throws IllegalAccessException;

    @PutMapping(path = API.BOOK_DELETE_ID)
    ResponseEntity<Response> deleteBook(@PathVariable String id);

    @GetMapping(path = API.BOOK_LIST)
    ResponseEntity<Response> getBookList(
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @RequestParam(value = "searchKey", required = false) SearchSortKey searchKey,
            @RequestParam(value = "sortKey", required = false) SearchSortKey sortKey,
            @RequestParam(value = "sort", required = false) Sort sort);


    @PostMapping(path = API.ISBN_LIST)
    ResponseEntity<Response> getIsbnList(@Valid @RequestBody IsbnListRequest request);

    @PostMapping(path = API.BOOK_LIST_SAVE)
    ResponseEntity<Response> saveBookList(@Valid @RequestBody AutoFillBookListRequest request);

}
