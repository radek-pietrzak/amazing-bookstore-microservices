package com.productservice.api.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.productservice.api.util.JsonFileToJsonObject;
import com.productservice.entity.Author;
import com.productservice.entity.Book;
import com.productservice.entity.Category;
import com.productservice.entity.Publisher;
import com.productservice.repository.BookRepository;
import com.productservice.api.request.AuthorRequest;
import com.productservice.api.request.BookRequest;
import com.productservice.api.response.BookResponse;
import com.productservice.api.response.BookResponseList;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
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
                .map(Category::valueOf)
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
                .languageCode(request.getLanguageCode())
                .build();

        repository.save(book);
    }

    @Scheduled(fixedRate = 60000)
    public void saveBookChatGPT() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonFileToJsonObject jsonFileToJsonObject = new JsonFileToJsonObject();
        String jsonRequest = jsonFileToJsonObject.readByFileName("request").get("validBook").toString();
        BookRequest request = objectMapper.readValue(jsonRequest, BookRequest.class);
        saveBook(request);
        System.out.println("Book saved successfully" + jsonRequest);
    }

    public void editBook(String id, BookRequest request) {
    }

    public void deleteBook(String id) {
    }

    public BookResponseList getBookList(String search) {
        return null;
    }
}
