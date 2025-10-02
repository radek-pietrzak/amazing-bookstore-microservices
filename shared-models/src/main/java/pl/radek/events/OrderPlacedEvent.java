package pl.radek.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderPlacedEvent {
    private String orderId;
    private List<OrderBookEvent> items;
    private String reservationUid;
}
