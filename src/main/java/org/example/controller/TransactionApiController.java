package org.example.controller;

import org.example.dto.ErrorResponse;
import org.example.dto.TransactionRequest;
import org.example.dto.TransactionSumResponse;
import org.example.model.ParentIdError;
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
        try{
            this.transactionService.create(transaction_id, request.amount(), request.type(),request.parent_id());
        }catch (ParentIdError e){
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping("/types/{type}")
    public List<Long> getTransactionIds(@PathVariable String type){
        return transactionService.getTransactionIdsType(type);
    }

    @GetMapping("/sum/{transaction_id}")
    public ResponseEntity<?> getTransactionSum(@PathVariable Long transaction_id){
        double sum = this.transactionService.getTransactionSum(transaction_id);
        return  ResponseEntity.ok(new TransactionSumResponse(sum));
    }
}
