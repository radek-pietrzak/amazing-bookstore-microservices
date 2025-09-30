package pl.radek.documents;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItem {
    private String isbn;
    private int quantity;
    private BigDecimal priceAtPurchase;
    private String title;
}