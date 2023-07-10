package com.productservice.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AutoFillBookListRequest {
    @JsonProperty(JsonPropertyNames.AUTO_FILL_BOOK_LIST)
    @Valid
    List<BookRequest> books;
}
