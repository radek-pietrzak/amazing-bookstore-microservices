package com.productservice.validation;

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
    String PAGE_COUNT_MIN = "Page Count must be greater than or equal to 1";
    String PAGE_COUNT_MAX = "Page Count must be less than or equal to 10000";
    String LANG_CODE = "Invalid language code";
    String AUTHOR_NAME_LENGTH = "Author Name length must be between 1 and 255";
    String AUTHOR_DESCRIPTION_LENGTH = "Author Description length must be between 1 and 1000";
    String CATEGORY_INVALID = "Invalid Category";


}
