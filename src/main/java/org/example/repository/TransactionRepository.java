package org.example.repository;
import org.example.model.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;
public interface TransactionRepository extends MongoRepository<Transaction,Integer> {
}
