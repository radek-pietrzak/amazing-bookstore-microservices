package com.productservice.api.response;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
@Component
public class SearchCriteria {



    public QueryPage getSearchCriteria(String search, Integer pageNo, Integer pageSize, String searchKey) {
        pageNo = pageNo == null ? 0 : pageNo;
        pageSize = pageSize == null ? 10 : pageSize;
        search = search == null ? "" : search;

        Set<String> searchKeys = new HashSet<>();
        if (searchKey == null) {
            searchKeys.add("isbn");
            searchKeys.add("title");
            searchKeys.add("authors.authorName");
            searchKeys.add("description");
            searchKeys.add("categories");
            searchKeys.add("publisher.publisherName");
        } else {
            searchKeys.add(searchKey);
        }

        Collection<Criteria> criteriaCollection = new HashSet<>();
        String finalSearch = search;
        searchKeys.forEach(s -> criteriaCollection.add(Criteria.where(s).regex(finalSearch, "i")));

        Criteria criteria = new Criteria().orOperator(criteriaCollection);

        Query queryPage = Query.query(criteria)
                .with(PageRequest.of(pageNo, pageSize));

        Query queryTotal = Query.query(criteria);

        return new QueryPage(queryPage, queryTotal, pageSize, pageNo);
    }

    public record QueryPage(Query queryPage, Query queryTotal, Integer pageSize, Integer pageNo) {
    }
}
