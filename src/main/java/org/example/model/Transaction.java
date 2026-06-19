package org.example.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("transactions")
public class Transaction {
    @Id
    private final long id;
    private final double amount;
    private final String type;
    private final Long parentId;

    public Transaction(long id, double amount, String type, Long parentId){
        this.id = id;
        this.amount = amount;
        this.type = type;
        this.parentId = parentId;
    }

    public long getId(){
        return this.id;
    }

    public double getAmount(){
        return this.amount;
    }

    public String getType(){
        return this.type;
    }

    public Long getParentId(){
        return this.parentId;
    }
}
