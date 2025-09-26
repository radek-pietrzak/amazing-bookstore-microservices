package pl.radek.request;


import lombok.Data;

@Data
public class BookRequest {
    private String isbn;
    private int quantity;
}
