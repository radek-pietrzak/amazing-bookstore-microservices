package pl.radek.client;

import org.springframework.stereotype.Component;
import pl.radek.response.InventoryResponse;

@Component
public class InventoryServiceClientFallback implements InventoryServiceClient {


    @Override
    public InventoryResponse getInventoryByIsbn(String isbn) {
        // TODO logger
        System.out.println("Inventory service is unavailable. Falling back for ISBN: " +  isbn);
        return new InventoryResponse();
    }
}
