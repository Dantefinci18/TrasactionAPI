package org.example.controller;

import org.example.dto.TransactionRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
public class TransactionApiController {
    @PutMapping("{transaction_id}")
    public ResponseEntity<?> createTransaction(@PathVariable Integer transaction_id, @RequestBody TransactionRequest request) {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/types/{type}")
    public ResponseEntity<?> getTransactionIds(@PathVariable String type){
        return ResponseEntity.ok().build();
    }
}
