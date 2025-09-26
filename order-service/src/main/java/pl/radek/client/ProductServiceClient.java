package pl.radek.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.radek.response.ProductResponse;

@FeignClient(name = "product-service")
public interface ProductServiceClient {

    @GetMapping("/api/product/book/{isbn}")
    ProductResponse getBookByIsbn(@PathVariable("isbn") String isbn);
}
