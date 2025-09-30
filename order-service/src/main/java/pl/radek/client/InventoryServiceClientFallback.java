package pl.radek.client;

import org.springframework.stereotype.Component;
import pl.radek.request.ReleaseRequest;
import pl.radek.request.ReservationRequest;
import pl.radek.response.InventoryResponse;
import pl.radek.response.ReservationResponse;
import pl.radek.response.Response;

@Component
public class InventoryServiceClientFallback implements InventoryServiceClient {


    @Override
    public InventoryResponse getInventoryByIsbn(String isbn) {
        // TODO logger
        System.out.println("Inventory service is unavailable. Falling back for ISBN: " +  isbn);
        return new InventoryResponse();
    }

    @Override
    public ReservationResponse reserveStock(ReservationRequest reservationRequest) {
        return null;
    }

    @Override
    public Response releaseStock(ReleaseRequest releaseRequest) {
        return null;
    }
}
