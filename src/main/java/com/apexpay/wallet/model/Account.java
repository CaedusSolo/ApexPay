package com.apexpay.wallet.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data // Lombok annotation for auto-generating getters, setters, toString, equals...
@NoArgsConstructor // Lombok annotation for auto-generating empty constructor
@AllArgsConstructor // Lombok annotation for auto-generating constructor with all properties
@Entity  // for spring boot to know this is a database table
@Table(name = "accounts")  // explicitly name the table in database

public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private BigDecimal balance;
    private String currency;
}
