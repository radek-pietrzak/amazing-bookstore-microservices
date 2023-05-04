package com.productservice.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.productservice.validation.annotation.BookCategory;
import com.productservice.validation.annotation.LanguageCode;
import com.productservice.validation.ValidationErrors;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.ISBN;

import java.util.List;

@JsonPropertyOrder({"isbn", "title", "description", "title", "description", "publishDate", "pageCount", "languageCode", "authors", "categories", "publisher"})
public class BookRequest {
    @JsonProperty("isbn")
    @NotNull(message = ValidationErrors.ISBN_NULL)
    @ISBN(message = ValidationErrors.ISBN_INVALID)
    private String ISBN;
    @JsonProperty("title")
    @NotNull(message = ValidationErrors.TITLE_NULL)
    @Size(min = 1, max = 255, message = ValidationErrors.TITLE_LENGTH)
    private String title;
    @JsonProperty("description")
    @NotNull(message = ValidationErrors.DESCRIPTION_NULL)
    @Size(min = 1, max = 1000, message = ValidationErrors.DESCRIPTION_LENGTH)
    private String description;
    @JsonProperty("publishYear")
    @NotNull(message = ValidationErrors.PUBLISH_YEAR_NULL)
    private Integer publishYear;
    @JsonProperty("pageCount")
    @NotNull(message = ValidationErrors.PAGE_COUNT_NULL)
    @Min(value = 1, message = ValidationErrors.PAGE_COUNT_MIN)
    @Max(value = 10000, message = ValidationErrors.PAGE_COUNT_MAX)
    private Integer pageCount;
    @JsonProperty("languageCode")
    @NotNull(message = ValidationErrors.LANGUAGE_NULL)
    @LanguageCode(message = ValidationErrors.LANG_CODE)
    private String languageCode;
    @JsonProperty("authors")
    @NotNull(message = ValidationErrors.AUTHORS_NULL)
    @Valid
    private List<AuthorRequest> authors;
    @JsonProperty("categories")
    @NotNull(message = ValidationErrors.CATEGORIES_NULL)
    @BookCategory(message = ValidationErrors.CATEGORY_INVALID)
    private List<String> categories;
    @JsonProperty("publisher")
    @NotNull(message = ValidationErrors.PUBLISHER_NULL)
    @Valid
    private PublisherRequest publisher;

    public BookRequest() {
    }

    private BookRequest(BookRequestBuilder builder) {
        this.ISBN = builder.ISBN;
        this.title = builder.title;
        this.description = builder.description;
        this.publishYear = builder.publishYear;
        this.pageCount = builder.pageCount;
        this.languageCode = builder.language;
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

    public Integer getPublishYear() {
        return publishYear;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public String getLanguageCode() {
        return languageCode;
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
        private Integer publishYear;
        private Integer pageCount;
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

        public BookRequestBuilder publishYear(Integer publishYear) {
            this.publishYear = publishYear;
            return this;
        }

        public BookRequestBuilder pageCount(Integer pageCount) {
            this.pageCount = pageCount;
            return this;
        }

        public BookRequestBuilder languageCode(String language) {
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
            this.publishYear = bookRequest.publishYear;
            this.pageCount = bookRequest.pageCount;
            this.language = bookRequest.languageCode;
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
