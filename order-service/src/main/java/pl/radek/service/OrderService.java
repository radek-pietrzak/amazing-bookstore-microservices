package pl.radek.service;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.radek.client.InventoryServiceClient;
import pl.radek.client.ProductServiceClient;
import pl.radek.documents.Order;
import pl.radek.documents.OrderItem;
import pl.radek.events.OrderPlacedEvent;
import pl.radek.exceptions.InsufficientStockException;
import pl.radek.exceptions.OrderCreationException;
import pl.radek.exceptions.ServiceUnavailableException;
import pl.radek.mapper.OrderMapper;
import pl.radek.repository.OrderRepository;
import pl.radek.request.*;
import pl.radek.response.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final InventoryServiceClient inventoryServiceClient;
    private final ProductServiceClient productServiceClient;
    private final OrderEventProducer orderEventProducer;
    private final OrderMapper orderMapper;

    public OrderService(OrderRepository orderRepository,
                        InventoryServiceClient inventoryServiceClient,
                        ProductServiceClient productServiceClient,
                        OrderEventProducer orderEventProducer,
                        OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.inventoryServiceClient = inventoryServiceClient;
        this.productServiceClient = productServiceClient;
        this.orderEventProducer = orderEventProducer;
        this.orderMapper = orderMapper;
    }

    public Response createOrder(OrderRequest request) {
        ReservationResponse reservation;
        try {
            reservation = inventoryServiceClient.reserveStock(createReservationRequest(request));

        } catch (FeignException ex) {
            if (ex.status() == 409) {
                throw new InsufficientStockException("Failed to reserve products: not enough stock.");
            }
            throw new ServiceUnavailableException("The warehouse service is temporarily unavailable. Please try again later.");
        }

        if (reservation == null || !"SUCCESS".equals(reservation.getStatus())) {
            throw new OrderCreationException("Failed to reserve products in stock.");
        }

        Order newOrder;
        List<OrderItem> orderItems = request.getBooks().stream()
                .map(this::createOrderItem)
                .collect(Collectors.toList());

        BigDecimal totalPrice = orderItems.stream()
                .map(item -> item.getPriceAtPurchase().multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        try {
            newOrder = orderMapper.toOrder(request, orderItems, totalPrice);
            Order savedOrder = orderRepository.save(newOrder);
            OrderPlacedEvent event = orderEventProducer.sendOrderPlacedEvent(orderMapper.toOrderPlacedEvent(savedOrder));
            orderEventProducer.sendOrderPlacedEvent(event);

            return orderMapper.toOrderResponse(savedOrder);

        } catch (Exception e) {
            inventoryServiceClient.releaseStock(orderMapper.toReleaseRequest(reservation.getReservationUid()));
            throw new OrderCreationException("Error creating order, reservation has been canceled.", e);
        }

    }


    private ReservationRequest createReservationRequest(OrderRequest request) {
        List<ItemRequest> items = request.getBooks().stream()
                .map(book -> ItemRequest.builder()
                        .isbn(book.getIsbn())
                        .quantity(book.getQuantity())
                        .build())
                .toList();

        return new ReservationRequest(items);
    }

    private OrderItem createOrderItem(BookRequest bookRequest) {
        InventoryResponse inventoryResponse = inventoryServiceClient.getInventoryByIsbn(bookRequest.getIsbn());
        log.debug("Inventory : {}", inventoryResponse);

        if (inventoryResponse == null || inventoryResponse.getQuantity() < bookRequest.getQuantity()) {
            throw new IllegalArgumentException("Book with ISBN " + bookRequest.getIsbn() + " is out of stock.");
        }

        ProductResponse productResponse = productServiceClient.getBookByIsbn(bookRequest.getIsbn());
        log.debug("Book : {}", productResponse);

        return orderMapper.toOrderItem(bookRequest, inventoryResponse, productResponse);
    }

    public Response getOrder(String orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NoSuchElementException("Order not found with id: " + orderId));

        return orderMapper.toOrderResponse(order);
    }

    public Response getOrderHistory(String userId) {
        List<Order> userOrders = orderRepository.findByUserId(userId);

        return new OrderHistoryResponse(orderMapper.toOrderResponseList(userOrders));
    }
}
