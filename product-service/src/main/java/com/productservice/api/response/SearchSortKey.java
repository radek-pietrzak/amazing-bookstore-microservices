package com.productservice.api.response;

public enum SearchSortKey {
    ISBN("isbn"),
    TITLE("title"),
    AUTHOR_NAME("authors.authorName"),
    DESCRIPTION("description"),
    CATEGORIES("categories"),
    PUBLISHER_NAME("publisher.publisherName");

    private final String key;

    SearchSortKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
