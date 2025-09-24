package pl.radek.productservice.api.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.radek.productservice.api.response.InventoryResponse;

@FeignClient(name = "inventory-service")
public interface InventoryService {
    @GetMapping("/api/inventory/{isbn}")
    InventoryResponse getInventoryByIsbn(@PathVariable("isbn") String isbn);
}
