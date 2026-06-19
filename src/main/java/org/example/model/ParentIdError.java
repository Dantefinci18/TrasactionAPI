package org.example.model;

public class ParentIdError extends RuntimeException {
    public ParentIdError() {
        super("parent_id don't have transaction_id");
    }
}
