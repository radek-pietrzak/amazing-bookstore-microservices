package pl.radek.inventoryservice.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuantityRequest {
    private String isbn;
    private int quantity;
}
