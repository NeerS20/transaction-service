package com.transaction.service.transaction.controller;

import com.transaction.service.transaction.dto.TransactionRequest;
import com.transaction.service.transaction.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController
{

    private final TransactionService service;

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody TransactionRequest transactionRequest)
    {
        return ResponseEntity.status(201).body(service.create(transactionRequest));
    }
}