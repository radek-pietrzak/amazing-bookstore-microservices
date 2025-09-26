package pl.radek.controller;


import org.springframework.web.bind.annotation.RestController;
import pl.radek.request.OrderRequest;
import pl.radek.response.Response;
import org.springframework.http.ResponseEntity;
import pl.radek.service.OrderService;

@RestController
public class OrderController implements OrderApi {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public ResponseEntity<Response> createOrder(OrderRequest request) {
        Response response = orderService.createOrder(request);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Response> getOrder(String orderId) {
        Response response = orderService.getOrder(orderId);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Response> getOrderHistory(String userId) {
        Response response = orderService.getOrderHistory(userId);
        return ResponseEntity.ok(response);
    }
}
