package com.productservice.api.criteria;

import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookListCriteria {
    private String search;
    private Integer pageNo;
    private Integer pageSize;
    private SearchSortKey searchKey;
    private SearchSortKey sortKey;
    private Sort sort;
    @Builder.Default
    private Set<SearchSortKey> searchKeys = new HashSet<>();

    public void fillDefaultValues() {
        pageNo = (pageNo == null || pageNo < 0) ? 0 : pageNo;
        pageSize = (pageSize == null || pageSize < 1) ? 10 : pageSize;
        search = search == null ? "" : search;
        sort = sort == null ? Sort.ASC : sort;
        sortKey = sortKey == null ? SearchSortKey.TITLE : sortKey;

        if (searchKey != null) {
            searchKeys.add(searchKey);
        } else {
            searchKeys.add(SearchSortKey.ISBN);
            searchKeys.add(SearchSortKey.TITLE);
            searchKeys.add(SearchSortKey.AUTHOR_NAME);
            searchKeys.add(SearchSortKey.DESCRIPTION);
            searchKeys.add(SearchSortKey.PUBLISHER_NAME);
        }
    }
}