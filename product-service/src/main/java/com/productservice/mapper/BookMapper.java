package com.productservice.mapper;

import com.productservice.api.request.BookRequest;
import com.productservice.api.response.BookResponse;
import com.productservice.document.Book;
import org.mapstruct.Mapper;

@Mapper
public interface BookMapper {
    Book bookRequestToBook(BookRequest bookRequest);
    BookResponse bookToBookResponse(BookResponse bookResponse);
}
