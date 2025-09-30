package pl.radek.inventoryservice.request;

import lombok.Data;

@Data
public class ItemRequest {
    private String isbn;
    private Integer quantity;
}
