package com.productservice.api.criteria;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SortCriteria {
    private Sort sort;
    private SearchSortKey sortKey;
}
