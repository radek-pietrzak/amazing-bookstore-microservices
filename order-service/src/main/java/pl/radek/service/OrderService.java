package pl.radek.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.radek.client.InventoryServiceClient;
import pl.radek.client.ProductServiceClient;
import pl.radek.documents.Order;
import pl.radek.documents.OrderItem;
import pl.radek.documents.OrderStatus;
import pl.radek.events.OrderBookEvent;
import pl.radek.events.OrderPlacedEvent;
import pl.radek.repository.OrderRepository;
import pl.radek.request.BookRequest;
import pl.radek.request.OrderRequest;
import pl.radek.response.OrderResponse;
import pl.radek.response.Response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderEventProducer orderEventProducer;
    private final InventoryServiceClient inventoryServiceClient;
    private final ProductServiceClient productServiceClient;

    public Response createOrder(OrderRequest request) {
        log.debug("Request to Create Order : {}", request);
        List<OrderItem> orderItems = request.getBooks().stream()
                .map(this::createOrderItem)
                .collect(Collectors.toList());

        log.debug("Order items : {}", orderItems);
        BigDecimal totalPrice = orderItems.stream()
                .map(item -> item.getPriceAtPurchase().multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        log.debug("Total price : {}", totalPrice);
        Order order = new Order();
        order.setUserId(request.getUserId());
        order.setItems(orderItems);
        order.setTotalPrice(totalPrice);
        order.setStatus(OrderStatus.CREATED);
        order.setShippingAddress(request.getAddress());
        order.setCreatedAt(LocalDateTime.now());

        Order savedOrder = orderRepository.save(order);

        List<OrderBookEvent> orderBookEvents = orderItems.stream()
                .map(item -> new OrderBookEvent(item.getIsbn(), item.getQuantity()))
                .collect(Collectors.toList());
        orderEventProducer.sendOrderPlacedEvent(new OrderPlacedEvent(savedOrder.getId(), orderBookEvents));

        return OrderResponse.builder()
                .orderId(savedOrder.getId())
                .userId(savedOrder.getUserId())
                .status(savedOrder.getStatus())
                .totalPrice(savedOrder.getTotalPrice())
                .createdAt(savedOrder.getCreatedAt())
                .build();
    }

    private OrderItem createOrderItem(BookRequest bookRequest) {
        var inventory = inventoryServiceClient.getInventoryByIsbn(bookRequest.getIsbn());
        log.debug("Inventory : {}", inventory);

        if (inventory == null || inventory.getQuantity() < bookRequest.getQuantity()) {
            throw new IllegalArgumentException("Book with ISBN " + bookRequest.getIsbn() + " is out of stock.");
        }

        var bookDetails = productServiceClient.getBookByIsbn(bookRequest.getIsbn());
        log.debug("Book : {}", bookDetails);

        OrderItem item = new OrderItem();
        item.setIsbn(bookRequest.getIsbn());
        item.setQuantity(bookRequest.getQuantity());
        item.setPriceAtPurchase(inventory.getPrice());
        item.setTitle(bookDetails.getTitle());
        return item;
    }

    public Response getOrder(String orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NoSuchElementException("Order not found with id: " + orderId));
        // TODO: implement mapper Order -> OrderResponse
        return null;
    }

    public Response getOrderHistory(String userId) {
        List<Order> userOrders = orderRepository.findByUserId(userId);
        // TODO: implement mapper List<Order> -> OrderHistoryResponse
        return null;
    }
}
