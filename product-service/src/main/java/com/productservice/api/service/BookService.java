package com.productservice.api.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.productservice.ChatGPTHelper;
import com.productservice.api.response.AuthorResponse;
import com.productservice.api.response.PublisherResponse;
import com.productservice.document.Author;
import com.productservice.document.Book;
import com.productservice.document.Category;
import com.productservice.document.Publisher;
import com.productservice.repository.BookRepository;
import com.productservice.api.request.AuthorRequest;
import com.productservice.api.request.BookRequest;
import com.productservice.api.response.BookResponse;
import com.productservice.api.response.BookResponseList;
import com.productservice.repository.BookRepositoryTemplate;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final BookRepository repository;
    private final BookRepositoryTemplate repositoryTemplate;
    private final Validator validator;

    public BookService(BookRepository repository, BookRepositoryTemplate repositoryTemplate, Validator validator) {
        this.repository = repository;
        this.repositoryTemplate = repositoryTemplate;
        this.validator = validator;
    }

    public BookResponse getBook(String id) {
        Optional<Book> optionalBook = repository.findById(id);
        AtomicReference<BookResponse> response = new AtomicReference<>(new BookResponse());
        optionalBook.ifPresent(b ->
                response.set(BookResponse.builder()
                        .id(b.getId())
                        .createdDate(b.getCreatedDate())
                                .lastEditDate(b.getLastEditDate())
                                .deletedDate(b.getDeletedDate())
                                .ISBN(b.getISBN())
                                .title(b.getTitle())
                                .description(b.getDescription())
                                .pageCount(b.getPageCount())
                                .languageCode(b.getLanguageCode())
                                .authors(b.getAuthors().stream()
                                        .map(a -> new AuthorResponse(a.getName(), a.getDescription()))
                                        .collect(Collectors.toList()))
                                .categories(b.getCategories().stream()
                                        .map(Enum::name)
                                        .collect(Collectors.toList()))
                                .publisher(new PublisherResponse(b.getPublisher().getPublisherName(), b.getPublisher().getDescription()))
                                .publishYear(b.getPublishYear())
                        .build()
                ));

        return response.get();
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
                .publishYear(request.getPublishYear())
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

    public BookResponseList getBookList(String search, Integer page, Integer pageSize) {
        if (page == null) {
            page = 0;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        if (search == null) {
            search = "";
        }

        PageRequest pageRequest = PageRequest.of(page, pageSize);

        long booksTotal = repositoryTemplate.countBySearchTerm(search);
        List<Book> books = repositoryTemplate.findBySearchTermAndPageRequest(search, pageRequest);

        List<BookResponse> list = books.stream()
                .map(b -> BookResponse.builder()
                        .id(b.getId())
                        .createdDate(b.getCreatedDate())
                        .lastEditDate(b.getLastEditDate())
                        .deletedDate(b.getDeletedDate())
                        .ISBN(b.getISBN())
                        .title(b.getTitle())
                        .authors(b.getAuthors().stream()
                                .map(a -> AuthorResponse.builder()
                                        .name(a.getName())
                                        .description(a.getDescription())
                                        .build())
                                .collect(Collectors.toList()))
                        .description(b.getDescription())
                        .categories(b.getCategories().stream()
                                .map(Enum::name)
                                .collect(Collectors.toList()))
                        .publisher(new PublisherResponse(b.getPublisher().getPublisherName(), b.getPublisher().getDescription()))
                        .publishYear(b.getPublishYear())
                        .pageCount(b.getPageCount())
                        .languageCode(b.getLanguageCode())
                        .build())
                .toList();

        return new BookResponseList(booksTotal, list.size(), list);
    }
}
