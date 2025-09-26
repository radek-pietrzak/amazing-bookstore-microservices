package pl.radek.productservice.api.service;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import pl.radek.productservice.api.criteria.BookListCriteria;
import pl.radek.productservice.api.criteria.BookSpecification;
import pl.radek.productservice.api.criteria.PageInfo;
import pl.radek.productservice.api.request.AutoFillBookListRequest;
import pl.radek.productservice.api.response.*;
import pl.radek.productservice.api.response.*;
import pl.radek.productservice.entity.Book;
import pl.radek.productservice.mapper.BookMapper;
import pl.radek.productservice.repository.BookRepository;
import pl.radek.productservice.api.request.BookRequest;
import jakarta.validation.Validator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
public class BookService {

    private final BookRepository repository;
    // TODO use validator
    private final Validator validator;
    private final BookMapper bookMapper;
    private final InventoryService inventoryService;

    public BookService(BookRepository repository, Validator validator, BookMapper bookMapper, InventoryService inventoryService) {
        this.repository = repository;
        this.validator = validator;
        this.bookMapper = bookMapper;
        this.inventoryService = inventoryService;
    }

    public Response getBookByIsbn(String isbn) {
        log.debug("getBookByIsbn isbn {}", isbn);
        return bookMapper.bookToBookResponse(getBookIfPresent(isbn));
    }

    public Response saveBook(BookRequest request) {
        Book book = bookMapper.bookRequestToBook(request);
        book.setCreatedDate(LocalDateTime.now());
        repository.save(book);
        return bookMapper.bookToBookResponse(book);
    }

    public Response editBook(String id, BookRequest request) throws IllegalAccessException {
        Book repoBook = getBookIfPresent(Long.valueOf(id));
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

    public Response deleteBook(String id) {
        Book book = getBookIfPresent(Long.valueOf(id));
        book.setDeletedDate(LocalDateTime.now());
        repository.save(book);
        return bookMapper.bookToBookResponse(book);
    }

    public BookResponseList getBookList(BookListCriteria bookListCriteria) {
        bookListCriteria.fillDefaultValues();

        Sort sort = Sort.by(Sort.Direction.fromString(bookListCriteria.getSort().name()), bookListCriteria.getSortKey().getKey());
        Pageable pageable = PageRequest.of(bookListCriteria.getPageNo(), bookListCriteria.getPageSize(), sort);

        Specification<Book> spec = new BookSpecification(bookListCriteria);

        Page<Book> booksPageInfo = repository.findAll(spec, pageable);
        List<Book> books = booksPageInfo.getContent();
        long totalSize = booksPageInfo.getTotalElements();

        List<BookResponse> list = books.stream()
                .map(bookMapper::bookToBookResponse)
                .toList();

        return buildBookResponseList(bookListCriteria, list, totalSize); // Ta metoda wymaga drobnych poprawek
    }

    private BookResponseList buildBookResponseList(BookListCriteria bookListCriteria, List<BookResponse> list, long totalSize) {
        int listSize = list.size();
        int pageSize = bookListCriteria.getPageSize();
        int pageNo = bookListCriteria.getPageNo();
        PageInfo pageInfo = new PageInfo(listSize, pageNo);
        boolean hasNextPage = listSize >= pageSize;
        int totalPages = (int) Math.ceil((double) totalSize / pageSize);

        return BookResponseList.builder()
                .pageInfo(pageInfo)
                .hasNextPage(hasNextPage)
                .totalPages(totalPages)
                .totalSize(totalSize)
                .bookResponseList(list)
                .build();
    }

    private Book getBookIfPresent(Long id) {
        if (StringUtils.isBlank(String.valueOf(id))) {
            throw new IllegalArgumentException();
        }

        Optional<Book> optionalBook = repository.findById(id);

        if (optionalBook.isEmpty()) {
            throw new NoSuchElementException();
        }
        return optionalBook.get();
    }

    private Book getBookIfPresent(String isbn) {
        log.debug("getBookIfPresent: isbn={}", isbn);
        if (StringUtils.isBlank(isbn)) {
            throw new IllegalArgumentException();
        }

        Optional<Book> optionalBook = repository.findByIsbn(isbn);
        log.debug("getBookIfPresent: optionalBook={}", optionalBook);

        if (optionalBook.isEmpty()) {
            throw new NoSuchElementException();
        }
        return optionalBook.get();
    }

    public Response getIsbnList(List<String> isbnList) {
        List<Book> books = repository.findByIsbnIn(isbnList);
        if (books.isEmpty()) {
            return new IsbnListResponse();
        } else {
            return new IsbnListResponse(books.stream()
                    .map(Book::getIsbn)
                    .toList());
        }
    }

    public Response saveBookList(AutoFillBookListRequest request) {
        List<Book> books = request.getBooks().stream()
                .map(bookMapper::bookRequestToBook)
                .toList();

        //TODO: set created date in auditable
        books.forEach(book -> book.setCreatedDate(LocalDateTime.now()));


        return new AutoFillBookListResponse(books.stream()
                .map(bookMapper::bookToBookResponse)
                .toList());
    }

    public InventoryResponse getInventoryByIsbn(String isbn) {
        try {
            return inventoryService.getInventoryByIsbn(isbn);
        } catch (FeignException e) {
            return new InventoryResponse();
        }
    }
}
