package org.example.repository;
import org.example.model.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface TransactionRepository extends MongoRepository<Transaction,Integer> {
    List<Transaction> findByType(String type);
}
