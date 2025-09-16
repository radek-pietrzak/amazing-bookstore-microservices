package com.productservice.api.criteria;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PageInfo {
    private Integer size;
    private Integer number;
}
