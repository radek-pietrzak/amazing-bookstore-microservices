package pl.radek.productservice.api.request;

import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class IsbnListRequest {
    //TODO some validation
    private List<String> isbn;
}
