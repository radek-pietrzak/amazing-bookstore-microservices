package com.productservice.api.request;

import jakarta.validation.constraints.NotNull;

public class AuthorRequest {

    @NotNull(message = "Author Name cannot be null")
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
