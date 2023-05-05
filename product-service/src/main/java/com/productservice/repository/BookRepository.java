package com.productservice.repository;

import com.productservice.config.MongoDBConfig;
import com.productservice.entity.Book;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends MongoRepository<Book, String> {
    List<Book> findAllByDeletedDateIsNull(PageRequest pageRequest);

    // TODO move this method to separate class
    default List<Book> findWithSearch(String search, PageRequest pageRequest) {
        if(search == null){
            search = "";
        }
        Query query = new Query();
        Criteria criteria = new Criteria().andOperator(
                Criteria.where("deletedDate").is(null),
                new Criteria().orOperator(
                        Criteria.where("title").regex(search, "i"),
                        Criteria.where("ISBN").regex(search, "i"),
                        Criteria.where("authors.name").regex(search, "i"),
                        Criteria.where("description").regex(search, "i"),
                        Criteria.where("categories.name").regex(search, "i"),
                        Criteria.where("publisher.name").regex(search, "i")
                )
        );
        query.addCriteria(criteria);
        MongoDBConfig mongoDBConfig = new MongoDBConfig();
        return mongoDBConfig.mongoTemplate().find(query.with(pageRequest), Book.class);
    }
}
