package pl.radek.productservice.api.service;

import org.springframework.stereotype.Component;
import pl.radek.productservice.api.response.InventoryResponse;

@Component
public class InventoryServiceFallback implements InventoryService {


    @Override
    public InventoryResponse getInventoryByIsbn(String isbn) {
        // TODO logger
        System.out.println("Inventory service is unavailable. Falling back for ISBN: " +  isbn);
        return new InventoryResponse();
    }
}
