package com.productservice.api.response;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class EditBookResponse extends BookResponse {
    private boolean modified;
}


