package com.apexpay.wallet.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "transaction_records")
public class TransactionRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long sourceAccountId;
    private long destinationAccountId;
    private BigDecimal amount;
    private String status;
    private LocalDateTime timestamp;
}
