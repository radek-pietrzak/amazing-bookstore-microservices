package pl.radek.request;

import lombok.Data;
import pl.radek.documents.Address;

import java.util.List;

@Data
public class OrderRequest {
    private String userId;
    private List<BookRequest> books;
    private Address address;
}
