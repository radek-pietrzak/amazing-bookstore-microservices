package com.productservice.api.request;

import jakarta.validation.constraints.NotNull;

public class PublisherRequest {
    @NotNull(message = "Publisher Name cannot be null")
    private String publisherName;
    private String description;

    public PublisherRequest() {
    }

    public PublisherRequest(String publisherName, String description) {
        this.publisherName = publisherName;
        this.description = description;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public String getDescription() {
        return description;
    }
}
