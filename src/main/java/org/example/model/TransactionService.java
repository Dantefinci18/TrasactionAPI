package org.example.model;

import org.example.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import  java.util.Optional;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository){
        this.transactionRepository = transactionRepository;
    }

    public void create(long transaction_id, double amount, String type, Long parenntId){
        if(parenntId !=null && this.transactionRepository.findById(transaction_id).isEmpty()){
            throw new ParentIdError();
        }
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

    public double getTransactionSum(long transactionId) {
        Optional<Transaction> transactionOptional = this.transactionRepository.findById(transactionId);
        if (transactionOptional.isEmpty()) {
            return 0;
        }
        return sumTransaction(transactionOptional.get());
    }

    private double sumTransaction(Transaction transaction) {
        double suma = transaction.getAmount();
        List<Transaction> subtransactions =
                this.transactionRepository.findByParentId(transaction.getId());
        for (Transaction subtransaction : subtransactions) {
            suma += sumTransaction(subtransaction); // reuse the object, no findById needed
        }
        return suma;
    }
}
