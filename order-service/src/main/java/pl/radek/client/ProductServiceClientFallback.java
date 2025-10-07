package pl.radek.client;

import pl.radek.response.ProductResponse;

public class ProductServiceClientFallback implements ProductServiceClient {
    @Override
    public ProductResponse getBookByIsbn(String isbn) {
        return new ProductResponse("Product description unavailable");
    }
}
