package pl.radek.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class InventoryResponse {
    private Integer quantity;
    private BigDecimal price;
}
