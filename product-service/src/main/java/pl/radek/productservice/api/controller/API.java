package pl.radek.productservice.api.controller;

public interface API {

    String API = "/api/product";
    String ID = "{id}";
    String ISBN = "{isbn}";
    String BOOK_GET = API + "/book/";
    String BOOK_GET_ISBN = BOOK_GET + ISBN;
    String BOOK_SAVE = API + "/book/save";
    String BOOK_EDIT = API + "/book/edit/";
    String BOOK_EDIT_ID = BOOK_EDIT + ID;
    String BOOK_DELETE = API + "/book/delete/";
    String BOOK_DELETE_ID = BOOK_DELETE + ID;
    String BOOK_LIST = API + "/book-list";
    String ISBN_LIST = API + "/isbn-list";
    String BOOK_LIST_SAVE = API + "/book-list/save";
    String GET_INVENTORY =  "api/inventory/{isbn}";

}
