package pl.radek.productservice.mapper;

import pl.radek.productservice.api.request.BookRequest;
import pl.radek.productservice.api.response.BookResponse;
import pl.radek.productservice.api.response.EditBookResponse;
import pl.radek.productservice.entity.Book;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookMapper {
    Book bookRequestToBook(BookRequest bookRequest);
    BookResponse bookToBookResponse(Book book);
    EditBookResponse bookToEditBookResponse(boolean modified, Book book);
}
