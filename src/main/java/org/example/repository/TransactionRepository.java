package org.example.repository;
import org.example.model.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends MongoRepository<Transaction,Long> {
    List<Transaction> findByType(String type);
    List<Transaction> findByParentId(Long parentId);
}
