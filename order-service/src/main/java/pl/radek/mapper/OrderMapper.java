package pl.radek.mapper;

import org.springframework.stereotype.Component;
import pl.radek.documents.Order;
import pl.radek.documents.OrderItem;
import pl.radek.documents.OrderStatus;
import pl.radek.events.OrderBookEvent;
import pl.radek.events.OrderPlacedEvent;
import pl.radek.request.BookRequest;
import pl.radek.request.OrderRequest;
import pl.radek.request.ReleaseRequest;
import pl.radek.response.InventoryResponse;
import pl.radek.response.OrderResponse;
import pl.radek.response.ProductResponse;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderMapper {

    public OrderResponse toOrderResponse(Order order) {
        if (order == null) {
            return null;
        }
        return OrderResponse.builder()
                .orderId(order.getId())
                .userId(order.getUserId())
                .status(order.getStatus())
                .totalPrice(order.getTotalPrice())
                .createdAt(order.getCreatedAt())
                .build();
    }

    public Order toOrder(OrderRequest request, List<OrderItem> orderItems, BigDecimal totalPrice) {
        if (request == null) {
            return null;
        }
        return Order.builder()
                .userId(request.getUserId())
                .items(orderItems)
                .totalPrice(totalPrice)
                .status(OrderStatus.CREATED)
                .shippingAddress(request.getAddress())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public List<OrderResponse> toOrderResponseList(List<Order> orders) {
        if (orders == null || orders.isEmpty()) {
            return Collections.emptyList();
        }

        return orders.stream()
                .map(this::toOrderResponse)
                .collect(Collectors.toList());
    }

    public OrderPlacedEvent toOrderPlacedEvent(Order order) {
        if (order == null) {
            return null;
        }

        List<OrderBookEvent> eventItems = order.getItems().stream()
                .map(item -> new OrderBookEvent(item.getIsbn(), item.getQuantity()))
                .collect(Collectors.toList());

        return new OrderPlacedEvent(order.getId(), eventItems);
    }

    public ReleaseRequest toReleaseRequest(String reservationUid) {
        if (reservationUid == null) {
            return null;
        }
        return new ReleaseRequest(reservationUid);
    }

    public OrderItem toOrderItem(BookRequest request, InventoryResponse inventoryResponse, ProductResponse productResponse) {
        if (request == null) {
            return null;
        }
        return OrderItem.builder()
                .isbn(request.getIsbn())
                .quantity(request.getQuantity())
                .priceAtPurchase(inventoryResponse.getPrice())
                .title(productResponse.getTitle())
                .build();

    }
}
