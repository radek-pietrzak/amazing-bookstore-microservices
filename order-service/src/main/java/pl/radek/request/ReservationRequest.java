package pl.radek.request;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ReservationRequest {
    private List<ItemRequest> items;
}
