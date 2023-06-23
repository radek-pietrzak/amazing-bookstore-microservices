package com.productservice.api.criteria;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SearchCriteria {
    private String search;
    private Set<SearchSortKey> searchKeys;
}
