package com.productservice.api.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.productservice.ChatGPTHelper;
import com.productservice.api.response.Response;
import com.productservice.document.Book;
import com.productservice.mapper.BookMapper;
import com.productservice.repository.BookRepository;
import com.productservice.api.request.BookRequest;
import com.productservice.api.response.BookResponse;
import com.productservice.api.response.BookResponseList;
import com.productservice.repository.BookRepositoryTemplate;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class BookService {

    private final BookRepository repository;
    private final BookRepositoryTemplate repositoryTemplate;
    private final Validator validator;
    private final BookMapper bookMapper;

    public BookService(BookRepository repository, BookRepositoryTemplate repositoryTemplate, Validator validator, BookMapper bookMapper) {
        this.repository = repository;
        this.repositoryTemplate = repositoryTemplate;
        this.validator = validator;
        this.bookMapper = bookMapper;
    }

    public Response getBook(String id) {
        if (StringUtils.isBlank(id)) {
            throw new IllegalArgumentException();
        }

        Optional<Book> optionalBook = repository.findById(id);

        if (optionalBook.isEmpty()) {
            throw new NoSuchElementException();
        }

        return bookMapper.bookToBookResponse(optionalBook.get());
    }

    //TODO return BookResponse in body
    public void saveBook(BookRequest request) {
        Book book = bookMapper.bookRequestToBook(request);
        book.setCreatedDate(LocalDateTime.now());
        repository.save(book);
    }

    public Response editBook(String id, BookRequest request) throws IllegalAccessException {
        if (StringUtils.isBlank(id)) {
            throw new IllegalArgumentException();
        }

        Optional<Book> optionalBook = repository.findById(id);
        if (optionalBook.isEmpty()) {
            throw new NoSuchElementException();
        }

        Book repoBook = optionalBook.get();
        Book reqeustBook = bookMapper.bookRequestToBook(request);
        if (isChangedAndSet(repoBook, reqeustBook)) {
            repoBook.setLastEditDate(LocalDateTime.now());
            repository.save(repoBook);
            return bookMapper.bookToEditBookResponse(true, repoBook);
        } else {
            return bookMapper.bookToEditBookResponse(false, repoBook);
        }
    }

    private boolean isChangedAndSet(Book repoBook, Book requestBook) throws IllegalAccessException {
        boolean isChange = false;
        Field[] fields = Book.class.getDeclaredFields();
        for (Field field : fields) {
            if (field.getName().equals("id")
                    || field.getName().equals("createdDate")
                    || field.getName().equals("lastEditDate")
                    || field.getName().equals("deletedDate")) {
                continue;
            }

            field.setAccessible(true);
            Object repoValue = field.get(repoBook);
            Object requestValue = field.get(requestBook);
            if (!Objects.equals(repoValue, requestValue)) {
                field.set(repoBook, requestValue);
                isChange = true;
            }
        }
        return isChange;
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

    public BookResponse deleteBook(String id) {
        Optional<Book> optionalBook = repository.findById(id);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            book.setDeletedDate(LocalDateTime.now());
            repository.save(book);
            return bookMapper.bookToBookResponse(book);
        }
        return null;
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
                .map(bookMapper::bookToBookResponse)
                .toList();

        return new BookResponseList(booksTotal, list.size(), list);
    }
}
