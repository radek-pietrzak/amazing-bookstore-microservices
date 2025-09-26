package pl.radek.response;

import lombok.Builder;
import lombok.Data;
import pl.radek.documents.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Data
public class OrderResponse implements Response {
    private String orderId;
    private String userId;
    private OrderStatus status;
    private BigDecimal totalPrice;
    private LocalDateTime createdAt;
}
