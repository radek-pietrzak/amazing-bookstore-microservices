package com.productservice.api.response;

public class AuthorResponse {
    private String name;
    private String description;

    public AuthorResponse(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
