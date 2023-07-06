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
public class GetDBIsbnListResponse implements Response {
    private List<String> isbn;
}
