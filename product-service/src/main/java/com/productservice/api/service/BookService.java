package com.productservice.api.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.productservice.ChatGPTHelper;
import com.productservice.entity.Author;
import com.productservice.entity.Book;
import com.productservice.entity.Category;
import com.productservice.entity.Publisher;
import com.productservice.repository.BookRepository;
import com.productservice.api.request.AuthorRequest;
import com.productservice.api.request.BookRequest;
import com.productservice.api.response.BookResponse;
import com.productservice.api.response.BookResponseList;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final BookRepository repository;
    private final Validator validator;

    public BookService(BookRepository repository, Validator validator) {
        this.repository = repository;
        this.validator = validator;
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

    @Scheduled(fixedRate = 10000)
    public void saveBookChatGPT() throws IOException {
        long atStart = System.currentTimeMillis();
        ObjectMapper objectMapper = new ObjectMapper();
        ChatGPTHelper chatGPTHelper = new ChatGPTHelper();
        String jsonRequest = chatGPTHelper.getBookExample();
        System.out.println(jsonRequest);
        BookRequest request = objectMapper.readValue(jsonRequest, BookRequest.class);
        Set<ConstraintViolation<BookRequest>> violations = validator.validate(request);
        if (!violations.isEmpty()) {
            System.out.println("Book is not valid: " + jsonRequest);
        } else {
            saveBook(request);
            System.out.println("Book is valid and saved successfully" + jsonRequest);
        }
        long atEnd = System.currentTimeMillis();
        System.out.println("Response from chatGPT in seconds: " + ((atEnd - atStart) / 1000));
    }

    public void editBook(String id, BookRequest request) {
    }

    public void deleteBook(String id) {
    }

    public BookResponseList getBookList(String search) {
        return null;
    }
}
