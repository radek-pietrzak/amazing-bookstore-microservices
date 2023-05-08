package com.productservice.repository;

import com.productservice.entity.Book;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends MongoRepository<Book, String> {

    String DEFAULT_QUERY = """
            {
                $and: [
                    {
                        deletedDate: null
                    },
                    { $or: [
                        {
                            title: {$regex:?0, $options:'i'}
                        },
                        {
                            ISBN: {$regex:?0, $options:'i'}
                        },
                        {
                            'authors.name': {$regex:?0, $options:'i'}
                        },
                        {
                            description: {$regex:?0, $options:'i'}
                        },
                        {
                            'categories.name': {$regex:?0, $options:'i'}
                        },
                        {
                            'publisher.name': {$regex:?0, $options:'i'}
                        }
                        ]
                    }
                ]
            }""";

    @Query(DEFAULT_QUERY)
    List<Book> findBySearchTermAndPageRequest(String search, PageRequest pageRequest);

}
