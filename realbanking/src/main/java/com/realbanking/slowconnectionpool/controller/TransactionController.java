package com.realbanking.slowconnectionpool.controller;

import com.realbanking.slowconnectionpool.model.Transaction;
import com.realbanking.slowconnectionpool.service.TransactionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public Object getTransactions() {
        List<Transaction> transactions = transactionService.getAllTransactions();
        
        if (transactions.isEmpty()) {
            return Map.of("message", "Success", "data", transactions);
        }
        
        return transactions;
    }
}
