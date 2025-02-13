package com.realbanking.slowconnectionpool.model;



import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String accountNumber;
    private Double amount;
    private String status;
}
