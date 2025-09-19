package pl.radek.inventoryservice.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@AllArgsConstructor
@ToString
@Getter
public class PriceAndQuantityResponse implements Response {
    private Integer quantity;
    private BigDecimal price;
}
