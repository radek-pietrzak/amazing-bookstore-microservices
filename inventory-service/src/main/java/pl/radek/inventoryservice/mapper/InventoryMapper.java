package pl.radek.inventoryservice.mapper;

import org.springframework.stereotype.Component;
import pl.radek.inventoryservice.entity.BookInventory;
import pl.radek.inventoryservice.entity.Reservation;
import pl.radek.inventoryservice.response.PriceAndQuantityResponse;
import pl.radek.inventoryservice.response.ReservationResponse;
import pl.radek.inventoryservice.response.Response;

@Component
public class InventoryMapper {

    public Response toPriceAndQuantity(BookInventory bookInventory) {
        if (bookInventory == null) {
            return null;
        }
        return new PriceAndQuantityResponse(bookInventory.getQuantity(), bookInventory.getPrice());
    }

    public Response toReservationResponse(Reservation reservation) {
        if (reservation == null) {
            return null;
        }
        return ReservationResponse.builder()
                .reservationUid(reservation.getReservationUid().toString())
                .status(reservation.getStatus().toString())
                .build();
    }
}
