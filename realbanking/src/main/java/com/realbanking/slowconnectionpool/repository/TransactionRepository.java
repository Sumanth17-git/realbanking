package com.realbanking.slowconnectionpool.repository;
import com.realbanking.slowconnectionpool.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}