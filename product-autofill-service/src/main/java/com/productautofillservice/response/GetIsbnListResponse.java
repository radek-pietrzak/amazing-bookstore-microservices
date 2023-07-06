package com.productautofillservice.response;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode
public class GetIsbnListResponse implements Response {
    private Integer numFound;
    private List<String> isbn;
}
