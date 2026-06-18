package org.example.model;

import org.example.repository.TransactionRepository;

public class TransactionService {
    public TransactionService(TransactionRepository transactionRepository){}
    public void create(Integer transaction_id, int amount, String type){}
}
