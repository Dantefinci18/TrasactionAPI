package org.example.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("transactions")
public class Transaction {
    @Id
    private final Integer id;
    private final int amount;
    private final String type;

    public Transaction(Integer id, int amount, String type){
        this.id = id;
        this.amount = amount;
        this.type = type;
    }

    public Integer getId(){
        return this.id;
    }

    public int getAmount(){
        return this.amount;
    }

    public String getType(){
        return this.type;
    }
}
