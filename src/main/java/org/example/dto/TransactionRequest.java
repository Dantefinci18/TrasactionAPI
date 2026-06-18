package org.example.dto;

public record TransactionRequest(int amount, String type, Integer parentId){}
