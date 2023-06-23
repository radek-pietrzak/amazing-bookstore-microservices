package com.productservice.api.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;

@Component
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class QueryProvider {

    private SearchCriteria searchCriteria;
    private PageCriteria pageCriteria;

    public QueryProvider withSearchCriteria(SearchCriteria searchCriteria) {
        this.searchCriteria = searchCriteria;
        return this;
    }

    public QueryProvider withPageCriteria(PageCriteria pageCriteria) {
        this.pageCriteria = pageCriteria;
        return this;
    }

    public Query getQuery() {
        Collection<Criteria> criteriaCollection = new HashSet<>();
        searchCriteria.getSearchKeys().forEach(s -> criteriaCollection.add(Criteria.where(s.getKey()).regex(searchCriteria.getSearch(), "i")));

        Criteria criteria = new Criteria().orOperator(criteriaCollection);
        Query query = new Query();

        if (pageCriteria != null) {
            query.addCriteria(criteria).with(PageRequest.of(pageCriteria.getPageNo(), pageCriteria.getPageSize()));
        }

        return query;
    }

}
