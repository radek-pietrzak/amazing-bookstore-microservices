package com.productservice;

public interface ValidationErrors {

    String ISBN_NULL = "ISBN cannot be null";
    String TITLE_NULL = "Title cannot be null";
    String DESCRIPTION_NULL = "Description cannot be null";
    String PUBLISH_DATE_NULL = "Publish Date cannot be null";
    String PAGE_COUNT_NULL = "Page Count cannot be null";
    String LANGUAGE_NULL = "Language cannot be null";
    String AUTHORS_NULL = "Authors cannot be null";
    String CATEGORIES_NULL = "Categories cannot be null";
    String PUBLISHER_NULL = "Publisher cannot be null";
    String AUTHOR_NAME_NULL = "Author Name cannot be null";
    String PUBLISHER_NAME_NULL = "Publisher Name cannot be null";
    String ISBN_INVALID = "Invalid ISBN";
    String TITLE_LENGTH = "Title length must be between 1 and 255";
    String DESCRIPTION_LENGTH = "Title length must be between 1 and 1000";
    String PUBLISH_DATE_FORMAT = "Invalid Publish Date format, expected format is yyyy-MM-dd";


}
