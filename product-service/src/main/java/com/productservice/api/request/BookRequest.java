package com.productservice.api.request;

import com.productservice.LocalDatePattern;
import com.productservice.ValidationErrors;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.ISBN;

import java.util.List;

public class BookRequest {
    @NotNull(message = ValidationErrors.ISBN_NULL)
    @ISBN(message = ValidationErrors.ISBN_INVALID)
    private String ISBN;
    @NotNull(message = ValidationErrors.TITLE_NULL)
    @Size(min = 1, max = 255, message = ValidationErrors.TITLE_LENGTH)
    private String title;
    @NotNull(message = ValidationErrors.DESCRIPTION_NULL)
    @Size(min = 1, max = 1000, message = ValidationErrors.DESCRIPTION_LENGTH)
    private String description;
    @NotNull(message = ValidationErrors.PUBLISH_DATE_NULL)
    @LocalDatePattern(message = ValidationErrors.PUBLISH_DATE_FORMAT)
    private String publishDate;
    @NotNull(message = ValidationErrors.PAGE_COUNT_NULL)
    private String pageCount;
    @NotNull(message = ValidationErrors.LANGUAGE_NULL)
    private String language;
    @NotNull(message = ValidationErrors.AUTHORS_NULL)
    @Valid
    private List<AuthorRequest> authors;
    @NotNull(message = ValidationErrors.CATEGORIES_NULL)
    private List<String> categories;
    @NotNull(message = ValidationErrors.PUBLISHER_NULL)
    @Valid
    private PublisherRequest publisher;

    public BookRequest() {
    }

    private BookRequest(BookRequestBuilder builder) {
        this.ISBN = builder.ISBN;
        this.title = builder.title;
        this.description = builder.description;
        this.publishDate = builder.publishDate;
        this.pageCount = builder.pageCount;
        this.language = builder.language;
        this.authors = builder.authors;
        this.categories = builder.categories;
        this.publisher = builder.publisher;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public String getPageCount() {
        return pageCount;
    }

    public String getLanguage() {
        return language;
    }

    public List<AuthorRequest> getAuthors() {
        return authors;
    }

    public List<String> getCategories() {
        return categories;
    }

    public PublisherRequest getPublisher() {
        return publisher;
    }

    public static BookRequestBuilder builder() {
        return new BookRequestBuilder();
    }

    public static class BookRequestBuilder {
        private String ISBN;
        private String title;
        private String description;
        private String publishDate;
        private String pageCount;
        private String language;
        private List<AuthorRequest> authors;
        private List<String> categories;
        private PublisherRequest publisher;

        public BookRequestBuilder ISBN(String ISBN) {
            this.ISBN = ISBN;
            return this;
        }

        public BookRequestBuilder title(String title) {
            this.title = title;
            return this;
        }

        public BookRequestBuilder description(String description) {
            this.description = description;
            return this;
        }

        public BookRequestBuilder publishDate(String publishDate) {
            this.publishDate = publishDate;
            return this;
        }

        public BookRequestBuilder pageCount(String pageCount) {
            this.pageCount = pageCount;
            return this;
        }

        public BookRequestBuilder language(String language) {
            this.language = language;
            return this;
        }

        public BookRequestBuilder authors(List<AuthorRequest> authors) {
            this.authors = authors;
            return this;
        }

        public BookRequestBuilder categories(List<String> categories) {
            this.categories = categories;
            return this;
        }

        public BookRequestBuilder publisher(PublisherRequest publisher) {
            this.publisher = publisher;
            return this;
        }

        public BookRequestBuilder bookRequest(BookRequest bookRequest) {
            this.ISBN = bookRequest.ISBN;
            this.title = bookRequest.title;
            this.description = bookRequest.description;
            this.publishDate = bookRequest.publishDate;
            this.pageCount = bookRequest.pageCount;
            this.language = bookRequest.language;
            this.authors = bookRequest.authors;
            this.categories = bookRequest.categories;
            this.publisher = bookRequest.publisher;
            return this;
        }

        public BookRequest build() {
            return new BookRequest(this);
        }
    }
}
