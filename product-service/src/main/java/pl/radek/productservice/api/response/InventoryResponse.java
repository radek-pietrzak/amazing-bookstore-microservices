package pl.radek.productservice.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class InventoryResponse implements Response {
    private Integer quantity;
    private BigDecimal price;
}
