package com.productservice.api.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@EqualsAndHashCode
@Getter
public class PublisherResponse {
    @Schema(description = "Publisher name", example = "Little, Brown and Company")
    private String publisherName;

    @Schema(description = "Publisher description", example = "Boston")
    private String publisherDescription;
}
