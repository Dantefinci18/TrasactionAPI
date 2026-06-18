package org.example.model;

import org.example.repository.TransactionRepository;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {
    private TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository){
        this.transactionRepository = transactionRepository;
    }

    public void create(Integer transaction_id, int amount, String type){
        transactionRepository.save(new Transaction(transaction_id,amount,type));
    }
}
