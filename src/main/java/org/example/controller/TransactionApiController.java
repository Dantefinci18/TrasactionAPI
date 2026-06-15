package org.example.controller;

import org.example.dto.TransactionRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
public class TransactionApiController {
    @PutMapping("{transaction_id}")
    public ResponseEntity<?> createTransaction(@RequestBody TransactionRequest request) {
        return ResponseEntity.ok().build();
    }
}
