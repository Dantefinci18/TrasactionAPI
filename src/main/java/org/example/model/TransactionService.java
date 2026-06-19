package org.example.model;

import org.example.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository){
        this.transactionRepository = transactionRepository;
    }

    public void create(long transaction_id, double amount, String type, Long parenntId){
        this.transactionRepository.save(new Transaction(transaction_id,amount,type,parenntId));
    }

    public ArrayList<Long> getTransactionIdsType(String type){
        List<Transaction> transactionsType = this.transactionRepository.findByType(type);
        ArrayList<Long> transactionsIdsType = new ArrayList<>();

        for(Transaction transaction: transactionsType){
            transactionsIdsType.add(transaction.getId());
        }

        return transactionsIdsType;
    }

    public double getTransactionSum(long tansaction_id){
        return 20000;
    }
}
