package pl.radek.inventoryservice.dto;

import org.springframework.stereotype.Component;
import pl.radek.inventoryservice.entity.BookInventory;
import pl.radek.inventoryservice.response.PriceAndQuantityResponse;
import pl.radek.inventoryservice.response.Response;

@Component
public class Dto {
    public Response bookInventoryToPriceAndQuantity(BookInventory bookInventory) {
        if (bookInventory == null) {
            return null;
        }
        return new PriceAndQuantityResponse(bookInventory.getQuantity(), bookInventory.getPrice());
    }
}
