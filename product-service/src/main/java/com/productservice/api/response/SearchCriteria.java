package com.productservice.api.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Component
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SearchCriteria {
    private String search;
    private Integer pageNo;
    private Integer pageSize;
    private Set<String> searchKeys;


    public QueryPage getQueryPage() {
        pageNo = pageNo == null ? 0 : pageNo;
        pageSize = pageSize == null ? 10 : pageSize;
        search = search == null ? "" : search;
        searchKeys = searchKeys == null ? new HashSet<>() : searchKeys;

        if (searchKeys.isEmpty() || (searchKeys.size() == 1 && searchKeys.contains(null))) {
            searchKeys.add("isbn");
            searchKeys.add("title");
            searchKeys.add("authors.authorName");
            searchKeys.add("description");
            searchKeys.add("categories");
            searchKeys.add("publisher.publisherName");
        }

        Collection<Criteria> criteriaCollection = new HashSet<>();
        searchKeys.forEach(s -> criteriaCollection.add(Criteria.where(s).regex(search, "i")));

        Criteria criteria = new Criteria().orOperator(criteriaCollection);

        Query queryPage = Query.query(criteria)
                .with(PageRequest.of(this.pageNo, this.pageSize));

        Query queryTotal = Query.query(criteria);

        return new QueryPage(queryPage, queryTotal);
    }

    public record QueryPage(Query queryPage, Query queryTotal) {
    }
}
