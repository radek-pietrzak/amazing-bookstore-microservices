package pl.radek.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import pl.radek.request.ReleaseRequest;
import pl.radek.request.ReservationRequest;
import pl.radek.response.InventoryResponse;
import pl.radek.response.ReservationResponse;
import pl.radek.response.Response;

// TODO to shared-models interface service name
@FeignClient(name = "inventory-service", fallback = InventoryServiceClientFallback.class)
public interface InventoryServiceClient {

    @GetMapping("/api/inventory/{isbn}")
    InventoryResponse getInventoryByIsbn(@PathVariable("isbn") String isbn);

    @PostMapping("api/inventory/reserve-stock")
    ReservationResponse reserveStock(@RequestBody ReservationRequest reservationRequest);

    @PostMapping("api/inventory/release-stock")
    Response releaseStock(@RequestBody ReleaseRequest releaseRequest);
}
