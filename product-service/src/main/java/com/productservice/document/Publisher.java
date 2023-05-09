package com.productservice.document;

import java.util.Objects;

public class Publisher {

    private String publisherName;
    private String description;

    public Publisher(String publisherName, String description) {
        this.publisherName = publisherName;
        this.description = description;
    }

    public Publisher() {
    }

    public String getPublisherName() {
        return publisherName;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Publisher publisher)) return false;
        return Objects.equals(getPublisherName(), publisher.getPublisherName()) && Objects.equals(getDescription(), publisher.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPublisherName(), getDescription());
    }
}
