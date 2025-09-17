package pl.radek.productservice.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import pl.radek.productservice.validation.annotation.BookCategory;
import pl.radek.productservice.validation.annotation.LanguageCode;
import pl.radek.productservice.validation.ValidationErrors;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.ISBN;

import java.util.List;
@Getter
@Setter
@JsonPropertyOrder({JsonPropertyNames.ISBN, JsonPropertyNames.TITLE, JsonPropertyNames.DESCRIPTION, JsonPropertyNames.PUBLISH_YEAR, JsonPropertyNames.PAGE_COUNT, JsonPropertyNames.LANG_CODE, JsonPropertyNames.PUBLISH_YEAR, JsonPropertyNames.AUTHORS, JsonPropertyNames.CATEGORIES, JsonPropertyNames.PUBLISHER})
public class BookRequest {
    @JsonProperty(JsonPropertyNames.ISBN)
    @NotNull(message = ValidationErrors.ISBN_NULL)
    @ISBN(message = ValidationErrors.ISBN_INVALID)
    @Schema(example = "9780316769488")
    private String isbn;

    @JsonProperty(JsonPropertyNames.TITLE)
    @NotNull(message = ValidationErrors.TITLE_NULL)
    @Size(min = 1, max = 255, message = ValidationErrors.TITLE_LENGTH)
    @Schema(example = "The Catcher in the Rye")
    private String title;

    @JsonProperty(JsonPropertyNames.AUTHORS)
    @NotNull(message = ValidationErrors.AUTHORS_NULL)
    @Valid
    private List<AuthorRequest> authors;

    @JsonProperty(JsonPropertyNames.DESCRIPTION)
    @NotNull(message = ValidationErrors.DESCRIPTION_NULL)
    @Size(min = 1, max = 1000, message = ValidationErrors.DESCRIPTION_LENGTH)
    @Schema(example = "The story of Holden Caulfield, a teenage boy who has been expelled from prep school and is wandering around New York City")
    private String description;

    @JsonProperty(JsonPropertyNames.CATEGORIES)
    @NotNull(message = ValidationErrors.CATEGORIES_NULL)
    @BookCategory(message = ValidationErrors.CATEGORY_INVALID)
    @Schema(example = "[\"FICTION\"]")
    private List<String> categories;

    @JsonProperty(JsonPropertyNames.PUBLISHER)
    @NotNull(message = ValidationErrors.PUBLISHER_NULL)
    @Valid
    private PublisherRequest publisher;

    @JsonProperty(JsonPropertyNames.PUBLISH_YEAR)
    @NotNull(message = ValidationErrors.PUBLISH_YEAR_NULL)
    @Schema(example = "1951")
    private Integer publishYear;

    @JsonProperty(JsonPropertyNames.PAGE_COUNT)
    @NotNull(message = ValidationErrors.PAGE_COUNT_NULL)
    @Min(value = 1, message = ValidationErrors.PAGE_COUNT_MIN)
    @Max(value = 10000, message = ValidationErrors.PAGE_COUNT_MAX)
    @Schema(example = "224")
    private Integer pageCount;

    @JsonProperty(JsonPropertyNames.LANG_CODE)
    @NotNull(message = ValidationErrors.LANGUAGE_NULL)
    @LanguageCode(message = ValidationErrors.LANG_CODE_INVALID)
    @Schema(example = "en")
    private String languageCode;

    public BookRequest() {
    }

    private BookRequest(BookRequestBuilder builder) {
        this.isbn = builder.isbn;
        this.title = builder.title;
        this.description = builder.description;
        this.publishYear = builder.publishYear;
        this.pageCount = builder.pageCount;
        this.languageCode = builder.language;
        this.authors = builder.authors;
        this.categories = builder.categories;
        this.publisher = builder.publisher;
    }

    public static BookRequestBuilder builder() {
        return new BookRequestBuilder();
    }

    public static class BookRequestBuilder {
        private String isbn;
        private String title;
        private String description;
        private Integer publishYear;
        private Integer pageCount;
        private String language;
        private List<AuthorRequest> authors;
        private List<String> categories;
        private PublisherRequest publisher;

        public BookRequestBuilder isbn(String isbn) {
            this.isbn = isbn;
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
            this.isbn = bookRequest.isbn;
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
