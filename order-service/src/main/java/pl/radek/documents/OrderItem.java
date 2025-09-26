package pl.radek.documents;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {
    private String isbn;
    private int quantity;
    private BigDecimal priceAtPurchase;
    private String title;
}