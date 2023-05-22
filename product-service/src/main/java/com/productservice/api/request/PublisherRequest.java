package com.productservice.api.request;

import com.productservice.validation.ValidationErrors;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PublisherRequest {
    @NotNull(message = ValidationErrors.PUBLISHER_NAME_NULL)
    @Size(min = 1, max = 255, message = ValidationErrors.PUBLISHER_NAME_LENGTH)
    @Schema(example = "Little, Brown and Company")
    private String publisherName;

    @Size(max = 1000, message = ValidationErrors.PUBLISHER_DESCRIPTION_LENGTH)
    @Schema(example = "Boston")
    private String description;
}
