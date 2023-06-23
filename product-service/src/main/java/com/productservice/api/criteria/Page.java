package com.productservice.api.criteria;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Page {
    private Integer size;
    private Integer number;
}
