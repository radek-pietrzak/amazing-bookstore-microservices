package com.productservice.api.response;

import com.productservice.api.criteria.Page;
import lombok.*;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class PageableResponse implements Response {
    private Page page;
    private Boolean hasNextPage;
    private Integer totalPages;
    private Long totalSize;
}
