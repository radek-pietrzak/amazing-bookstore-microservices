package com.productservice.api.request;

public class AuthorRequest {
    private String name;
    private String description;

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
