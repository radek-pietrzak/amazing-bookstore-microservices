package pl.radek.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.radek.response.InventoryResponse;

@FeignClient(name = "inventory-service", fallback = InventoryServiceClientFallback.class)
public interface InventoryServiceClient {

    @GetMapping("/api/inventory/{isbn}")
    InventoryResponse getInventoryByIsbn(@PathVariable("isbn") String isbn);
}
