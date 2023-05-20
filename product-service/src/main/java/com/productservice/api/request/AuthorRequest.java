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
public class AuthorRequest {

    @NotNull(message = ValidationErrors.AUTHOR_NAME_NULL)
    @Size(min = 1, max = 255, message = ValidationErrors.AUTHOR_NAME_LENGTH)
    @Schema(example = "J.D.")
    private String name;

    @Size(min = 1, max = 1000, message = ValidationErrors.AUTHOR_DESCRIPTION_LENGTH)
    @Schema(example = "Salinger")
    private String description;
}
