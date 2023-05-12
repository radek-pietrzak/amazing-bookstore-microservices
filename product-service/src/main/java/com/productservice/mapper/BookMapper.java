package com.productservice.mapper;

import com.productservice.api.request.BookRequest;
import com.productservice.api.response.BookResponse;
import com.productservice.document.Book;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookMapper {
    Book bookRequestToBook(BookRequest bookRequest);
    BookResponse bookToBookResponse(Book book);
}
