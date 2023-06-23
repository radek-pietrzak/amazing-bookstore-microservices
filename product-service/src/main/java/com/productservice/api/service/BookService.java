package com.productservice.api.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.productservice.ChatGPTHelper;
import com.productservice.api.controller.BookListCriteria;
import com.productservice.api.response.*;
import com.productservice.document.Book;
import com.productservice.mapper.BookMapper;
import com.productservice.repository.BookRepository;
import com.productservice.api.request.BookRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class BookService {

    private final BookRepository repository;
    private final Validator validator;
    private final BookMapper bookMapper;
    private final MongoOperations mongoOperations;

    public BookService(BookRepository repository,
                       Validator validator,
                       BookMapper bookMapper,
                       MongoOperations mongoOperations) {
        this.repository = repository;
        this.validator = validator;
        this.bookMapper = bookMapper;
        this.mongoOperations = mongoOperations;
    }

    public Response getBook(String id) {
        return bookMapper.bookToBookResponse(getBookIfPresent(id));
    }

    public Response saveBook(BookRequest request) {
        Book book = bookMapper.bookRequestToBook(request);
        book.setCreatedDate(LocalDateTime.now());
        repository.save(book);
        return bookMapper.bookToBookResponse(book);
    }

    public Response editBook(String id, BookRequest request) throws IllegalAccessException {
        Book repoBook = getBookIfPresent(id);
        Book requestBook = bookMapper.bookRequestToBook(request);
        if (updateChangedFields(repoBook, requestBook)) {
            repoBook.setLastEditDate(LocalDateTime.now());
            repository.save(repoBook);
            return bookMapper.bookToEditBookResponse(true, repoBook);
        } else {
            return bookMapper.bookToEditBookResponse(false, repoBook);
        }
    }

    private boolean updateChangedFields(Book repoBook, Book requestBook) throws IllegalAccessException {
        boolean isChanged = false;
        Field[] fields = Book.class.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            String fieldName = field.getName();
            Object repoValue = field.get(repoBook);
            Object requestValue = field.get(requestBook);

            if (isFieldIgnored(fieldName) || Objects.equals(repoValue, requestValue)) {
                continue;
            }

            field.set(repoBook, requestValue);
            isChanged = true;
        }
        return isChanged;
    }

    private boolean isFieldIgnored(String fieldName) {
        Set<String> ignoredFields = new HashSet<>(Arrays.asList("id", "createdDate", "lastEditDate", "deletedDate"));
        return ignoredFields.contains(fieldName);
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

    public Response deleteBook(String id) {
        Book book = getBookIfPresent(id);
        book.setDeletedDate(LocalDateTime.now());
        repository.save(book);
        return bookMapper.bookToBookResponse(book);
    }

    public BookResponseList getBookList(BookListCriteria bookListCriteria) {
        bookListCriteria.fillDefaultValues();
        bookListCriteria.fillCriteria();

        List<Book> books = mongoOperations.find(bookListCriteria.getFindQuery(), Book.class);
        long totalSize = mongoOperations.count(bookListCriteria.getCountQuery(), Book.class);

        List<BookResponse> list = books.stream()
                .map(bookMapper::bookToBookResponse)
                .toList();

        return buildBookResponseList(bookListCriteria, list, totalSize);
    }

    private BookResponseList buildBookResponseList(BookListCriteria bookListCriteria, List<BookResponse> list, long totalSize) {
        int listSize = list.size();
        int pageSize = bookListCriteria.getPageCriteria().getPageSize();
        int pageNo = bookListCriteria.getPageCriteria().getPageNo();
        Page page = new Page(listSize, pageNo);
        boolean hasNextPage = listSize >= pageSize;
        int totalPages = (int) Math.ceil((double) totalSize / pageSize);

        return BookResponseList.builder()
                .page(page)
                .hasNextPage(hasNextPage)
                .totalPages(totalPages)
                .totalSize(totalSize)
                .bookResponseList(list)
                .build();
    }

    private Book getBookIfPresent(String id) {
        if (StringUtils.isBlank(id)) {
            throw new IllegalArgumentException();
        }

        Optional<Book> optionalBook = repository.findById(id);

        if (optionalBook.isEmpty()) {
            throw new NoSuchElementException();
        }
        return optionalBook.get();
    }
}
