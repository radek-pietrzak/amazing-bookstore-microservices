package pl.radek.inventoryservice.controller;

public interface API {

    String API = "/api/inventory/";
    String ISBN = API + "{isbn}";
    String STOCK_CHECK = API + "/stock-check";
    String INVENTORY_ADD = API + "/add";
    String INVENTORY_UPDATE = API + "/update";
    String INVENTORY_DECREMENT = API + "/decrement";
    String INVENTORY_INCREMENT = API + "/increment";


}
