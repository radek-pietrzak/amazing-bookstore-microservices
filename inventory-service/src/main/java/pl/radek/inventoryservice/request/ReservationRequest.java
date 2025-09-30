package pl.radek.inventoryservice.request;

import lombok.Data;

import java.util.List;

@Data
public class ReservationRequest {
    private List<ItemRequest> items;
}
