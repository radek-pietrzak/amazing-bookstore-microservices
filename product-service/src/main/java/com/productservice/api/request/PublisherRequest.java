package com.productservice.api.request;

public class PublisherRequest {
    private String publisherName;
    private String description;

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
