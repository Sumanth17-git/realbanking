package com.realbanking.slowconnectionpool.service;

import com.realbanking.slowconnectionpool.model.Transaction;
import com.realbanking.slowconnectionpool.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {
    
    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<Transaction> getAllTransactions() {
        try {
            // Simulating database slowness
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return transactionRepository.findAll();
    }
}
