package org.example.dto;

public record TransactionRequest(double amount, String type, Long parentId){}
