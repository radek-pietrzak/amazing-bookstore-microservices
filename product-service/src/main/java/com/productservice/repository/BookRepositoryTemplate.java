package com.productservice.repository;

import com.productservice.config.MongoDBConfig;
import com.productservice.document.Book;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class BookRepositoryTemplate {

    private final MongoTemplate mongoTemplate;

    public BookRepositoryTemplate(MongoDBConfig mongoDBConfig) {
        this.mongoTemplate = mongoDBConfig.mongoTemplate();
    }

    public List<Book> findBySearchTermAndPageRequest(String search, PageRequest pageRequest) {
        return mongoTemplate.find(bySearchQuery(search).with(pageRequest), Book.class);
    }

    public long countBySearchTerm(String searchTerm) {
        return mongoTemplate.count(bySearchQuery(searchTerm), Book.class);
    }

    private Query bySearchQuery(String searchTerm) {
        Query query = new Query();
        Criteria criteria = new Criteria().andOperator(
                Criteria.where("deletedDate").is(null),
                new Criteria().orOperator(
                        Criteria.where("title").regex(searchTerm, "i"),
                        Criteria.where("ISBN").regex(searchTerm, "i"),
                        Criteria.where("authors.name").regex(searchTerm, "i"),
                        Criteria.where("description").regex(searchTerm, "i"),
                        Criteria.where("categories.name").regex(searchTerm, "i"),
                        Criteria.where("publisher.name").regex(searchTerm, "i")
                )
        );
        query.addCriteria(criteria);
        return query;
    }
}
