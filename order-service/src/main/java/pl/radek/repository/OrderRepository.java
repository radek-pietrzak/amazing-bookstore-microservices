package pl.radek.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.radek.documents.Order;

import java.util.List;

public interface OrderRepository extends MongoRepository<Order, String> {
    List<Order> findByUserId(String userId);
}
