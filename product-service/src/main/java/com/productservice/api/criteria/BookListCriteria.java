package com.productservice.api.criteria;

import lombok.*;
import org.springframework.data.mongodb.core.query.Query;

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
    private Set<SearchSortKey> searchKeys = new HashSet<>();
    private SearchCriteria searchCriteria;
    private SortCriteria sortCriteria;
    private PageCriteria pageCriteria;

    public void fillDefaultValues() {
        pageNo = pageNo == null ? 0 : pageNo;
        pageSize = pageSize == null ? 10 : pageSize;
        search = search == null ? "" : search;
        sort = sort == null ? Sort.ASC : sort;
        sortKey = sortKey == null ? SearchSortKey.TITLE : sortKey;
        searchKeys = searchKeys == null ? new HashSet<>() : searchKeys;

        if (searchKey == null) {
            searchKeys.add(SearchSortKey.ISBN);
            searchKeys.add(SearchSortKey.TITLE);
            searchKeys.add(SearchSortKey.AUTHOR_NAME);
            searchKeys.add(SearchSortKey.DESCRIPTION);
            searchKeys.add(SearchSortKey.CATEGORIES);
            searchKeys.add(SearchSortKey.PUBLISHER_NAME);
        } else {
            searchKeys.add(searchKey);
        }
    }

    public void fillCriteria() {
        searchCriteria = SearchCriteria.builder()
                .search(search)
                .searchKeys(searchKeys)
                .build();

        sortCriteria = SortCriteria.builder()
                .sort(sort)
                .sortKey(sortKey)
                .build();

        pageCriteria = PageCriteria.builder()
                .pageNo(pageNo)
                .pageSize(pageSize)
                .build();
    }

    public Query getFindQuery() {
        return new QueryProvider()
                .withSearchCriteria(searchCriteria)
                .withPageCriteria(pageCriteria)
                .withSortCriteria(sortCriteria)
                .getQuery();
    }

    public Query getCountQuery() {
        return new QueryProvider()
                .withSearchCriteria(searchCriteria)
                .getQuery();
    }

}
