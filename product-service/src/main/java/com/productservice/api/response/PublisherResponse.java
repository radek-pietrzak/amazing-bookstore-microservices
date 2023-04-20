package com.productservice.api.response;

public class PublisherResponse {
    private String publisherName;
    private String description;

    public PublisherResponse(String publisherName, String description) {
        this.publisherName = publisherName;
        this.description = description;
    }
}
