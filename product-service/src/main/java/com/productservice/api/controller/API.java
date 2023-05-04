package com.productservice.api.controller;

public interface API {

    String BOOK = "/product-service/book/{id}";
    String BOOK_SAVE = "/product-service/book/save";
    String BOOK_EDIT = "/product-service/book/edit/{id}";
    String BOOK_DELETE = "/product-service/book/delete/{id}";
    String BOOK_LIST = "/product-service/book-list";

    String REPAIR_PUBLISH_YEAR = "/product-service/repair-publish-year";
}
