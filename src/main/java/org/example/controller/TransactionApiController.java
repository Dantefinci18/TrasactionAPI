package org.example.controller;

import org.example.dto.TransactionIdsResponse;
import org.example.dto.TransactionRequest;
import org.example.model.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionApiController {
    private final TransactionService transactionService;

    public TransactionApiController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PutMapping("{transaction_id}")
    public ResponseEntity<?> createTransaction(@PathVariable Long transaction_id, @RequestBody TransactionRequest request) {
        this.transactionService.create(transaction_id,request.amount(),request.type(),request.parentId());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/types/{type}")
    public ResponseEntity<?> getTransactionIds(@PathVariable String type){
        List<Long> ids = transactionService.getTransactionIdsType(type);
        return ResponseEntity.ok(new TransactionIdsResponse(ids));
    }
}
