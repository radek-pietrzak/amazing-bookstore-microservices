package pl.radek.controller;

import pl.radek.request.OrderRequest;
import pl.radek.response.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface OrderApi {

    @PostMapping(path = API.ORDER_POST)
    ResponseEntity<Response> createOrder(@RequestBody OrderRequest request);

    @GetMapping(path = API.ORDER_ID)
    ResponseEntity<Response> getOrder(@PathVariable String orderId);

    @GetMapping(path = API.ORDERS_USER_GET)
    ResponseEntity<Response> getOrderHistory(@PathVariable String userId);
}
