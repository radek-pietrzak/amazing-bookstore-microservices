package com.productservice.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class AuthorRequest {

    @JsonProperty(JsonPropertyNames.AUTHOR_NAME)
    @NotNull(message = ValidationErrors.AUTHOR_NAME_NULL)
    @Size(min = 1, max = 255, message = ValidationErrors.AUTHOR_NAME_LENGTH)
    @Schema(example = "J.D.")
    private String authorName;

    @JsonProperty(JsonPropertyNames.AUTHOR_DESCRIPTION)
    @Size(min = 1, max = 1000, message = ValidationErrors.AUTHOR_DESCRIPTION_LENGTH)
    @Schema(example = "Salinger")
    private String authorDescription;
}
