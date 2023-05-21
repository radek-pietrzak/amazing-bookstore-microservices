package com.productservice.api.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(exclude = {"lastEditDate"})
public class BookResponse implements Response{
    @Schema(description = "Book ID", example = "6448f44759850b6631d6a271")
    private String id;

    @Schema(description = "Date of creation", example = "2023-04-26T11:52:07.257")
    private LocalDateTime createdDate;

    @Schema(description = "Date of last edit", example = "2023-05-21T12:45:00")
    private LocalDateTime lastEditDate;

    @Schema(description = "Date of deletion", example = "2023-05-22T14:00:00")
    private LocalDateTime deletedDate;

    @Schema(description = "ISBN", example = "9780316769488")
    private String isbn;

    @Schema(description = "Book title", example = "The Catcher in the Rye")
    private String title;

    private List<AuthorResponse> authors;

    @Schema(description = "Book description", example = "The story of Holden Caulfield, a teenage boy who has been expelled from prep school and is wandering around New York City.")
    private String description;

    @Schema(description = "List of categories", example = "[\"FICTION\"]")
    private List<String> categories;

    private PublisherResponse publisher;

    @Schema(description = "Year of publication", example = "1951")
    private Integer publishYear;

    @Schema(description = "Number of pages", example = "224")
    private Integer pageCount;

    @Schema(description = "Language code", example = "en")
    private String languageCode;
}
