package pl.radek.inventoryservice.controller;

public interface API {

    String API = "/api/inventory";
    String ISBN = API + "/{isbn}";
    String STOCK_CHECK = API + "/stock-check";
    String INVENTORY_ADD = API + "/add";
    String INVENTORY_UPDATE = API + "/update";
    String DECREMENT_STOCK = API + "/decrement";
    String INCREMENT_STOCK = API + "/increment";
    String RESERVE_STOCK = API + "/reserve-stock";
    String RELEASE_STOCK = API + "/release-stock";


}
