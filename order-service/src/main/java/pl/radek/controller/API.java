package pl.radek.controller;

public interface API {

    String API = "/api/orders";
    String ORDER_ID = "/{orderId}";
    String USER_ID = "/user/{userId}";
    String ORDER_POST = API;
    String ORDERS_USER_GET = API + USER_ID;
}
