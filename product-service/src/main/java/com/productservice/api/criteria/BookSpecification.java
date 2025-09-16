package com.productservice.api.criteria;

import com.productservice.entity.Author;
import com.productservice.entity.Book;
import com.productservice.entity.Publisher;
import jakarta.persistence.criteria.*;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class BookSpecification implements Specification<Book> {

    private final BookListCriteria criteria;

    @Override
    public Predicate toPredicate(Root<Book> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        if (StringUtils.isBlank(criteria.getSearch())) {
            return criteriaBuilder.conjunction();
        }

        List<Predicate> predicates = new ArrayList<>();
        String searchPattern = "%" + criteria.getSearch().toLowerCase() + "%";

        for (SearchSortKey key : criteria.getSearchKeys()) {
            String keyString = key.getKey();
            Path<?> path;

            if (keyString.contains(".")) {
                String[] parts = keyString.split("\\.");
                Join<Book, Author> authorJoin = root.join(parts[0], JoinType.LEFT); // np. join do "authors"
                path = authorJoin.get(parts[1]);
            } else {
                path = root.get(keyString);
            }

            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(path.as(String.class)), searchPattern));
        }

        return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
    }
}