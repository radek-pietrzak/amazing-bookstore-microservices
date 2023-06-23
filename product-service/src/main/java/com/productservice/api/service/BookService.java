package com.productservice.api.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.productservice.ChatGPTHelper;
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
        if (isChangedAndSet(repoBook, requestBook)) {
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

    public Response deleteBook(String id) {
        Book book = getBookIfPresent(id);
        book.setDeletedDate(LocalDateTime.now());
        repository.save(book);
        return bookMapper.bookToBookResponse(book);
    }

    public BookResponseList getBookList(String search, Integer pageNo, Integer pageSize) {
        pageNo = pageNo == null ? 0 : pageNo;
        pageSize = pageSize == null ? 10 : pageSize;
        search = search == null ? "" : search;
        Set<SearchKey> searchKeys = new HashSet<>();

        searchKeys.add(SearchKey.ISBN);
        searchKeys.add(SearchKey.TITLE);
        searchKeys.add(SearchKey.AUTHOR_NAME);
        searchKeys.add(SearchKey.DESCRIPTION);
        searchKeys.add(SearchKey.CATEGORIES);
        searchKeys.add(SearchKey.PUBLISHER_NAME);

        SearchCriteria searchCriteria = SearchCriteria.builder()
                .search(search)
                .searchKeys(searchKeys)
                .build();

        PageCriteria pageCriteria = PageCriteria.builder()
                .pageNo(pageNo)
                .pageSize(pageSize)
                .build();

        List<Book> books = mongoOperations.find(new QueryProvider()
                        .withSearchCriteria(searchCriteria)
                        .withPageCriteria(pageCriteria)
                        .getQuery(),
                Book.class);

        long totalSize = mongoOperations.count(new QueryProvider()
                        .withSearchCriteria(searchCriteria)
                        .getQuery(),
                Book.class);

        List<BookResponse> list = books.stream()
                .map(bookMapper::bookToBookResponse)
                .toList();

        return BookResponseList.builder()
                .page(new com.productservice.api.response.Page(
                                books.size(),
                                pageCriteria.getPageNo()
                        )
                )
                .hasNextPage(list.size() >= pageCriteria.getPageSize())
                .totalPages((int) Math.ceil((double) totalSize / pageCriteria.getPageSize()))
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
