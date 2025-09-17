package pl.radek.productservice.api.controller;

public interface API {

    String ID = "{id}";
    String BOOK_GET = "/product-service/book/";
    String BOOK_GET_ID = BOOK_GET + ID;
    String BOOK_SAVE = "/product-service/book/save";
    String BOOK_EDIT = "/product-service/book/edit/";
    String BOOK_EDIT_ID = BOOK_EDIT + ID;
    String BOOK_DELETE = "/product-service/book/delete/";
    String BOOK_DELETE_ID = BOOK_DELETE + ID;
    String BOOK_LIST = "/product-service/book-list";
    String ISBN_LIST = "/product-service/isbn-list";
    String BOOK_LIST_SAVE = "/product-service/book-list/save";

}
