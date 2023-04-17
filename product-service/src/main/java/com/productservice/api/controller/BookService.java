package com.productservice.api.controller;

import com.productservice.Author;
import com.productservice.Book;
import com.productservice.Category;
import com.productservice.Publisher;
import com.productservice.api.repository.BookRepository;
import com.productservice.api.request.AuthorRequest;
import com.productservice.api.request.BookRequest;
import com.productservice.api.response.BookResponse;
import com.productservice.api.response.BookResponseList;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final BookRepository repository;

    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    public BookResponse getBook(String id) {
        return null;
    }

    public void saveBook(BookRequest request) {

        List<AuthorRequest> requestAuthors = request.getAuthors();
        List<Author> authors = requestAuthors.stream()
                .map(a -> new Author(a.getName(), a.getDescription()))
                .collect(Collectors.toList());

        List<String> requestCategories = request.getCategories();
        List<Category> categories = requestCategories.stream()
                .map(Category::getCategoryByName)
                .toList();

        Publisher publisher = new Publisher(request.getPublisher().getPublisherName(), request.getPublisher().getDescription());

        int pageCount = request.getPageCount();

        Book book = new Book.BookBuilder()
                .createdDate(LocalDateTime.now())
                .ISBN(request.getISBN())
                .title(request.getTitle())
                .authors(authors)
                .description(request.getDescription())
                .categories(categories)
                .publisher(publisher)
                .publishDate(LocalDate.parse(request.getPublishDate()))
                .pageCount(pageCount)
                .language(request.getLanguage())
                .build();

        repository.save(book);
    }

    public void editBook(String id, BookRequest request) {
    }

    public void deleteBook(String id) {
    }

    public BookResponseList getBookList(String search) {
        return null;
    }
}
