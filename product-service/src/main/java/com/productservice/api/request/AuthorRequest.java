package com.productservice.api.request;

import com.productservice.validation.ValidationErrors;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class AuthorRequest {

    @NotNull(message = ValidationErrors.AUTHOR_NAME_NULL)
    @Size(min = 1, max = 255, message = ValidationErrors.AUTHOR_NAME_LENGTH)
    private String name;
    private String description;

    public AuthorRequest() {
    }

    public AuthorRequest(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
