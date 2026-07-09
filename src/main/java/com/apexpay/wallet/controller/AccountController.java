package com.apexpay.wallet.controller;
import com.apexpay.wallet.dto.TransferRequest;
import com.apexpay.wallet.model.Account;
import com.apexpay.wallet.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    // POST /api/v1/accounts
    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestParam BigDecimal initialBalance, @RequestParam String currency) {
        Account newAccount =accountService.createAccount(initialBalance, currency);
        return new ResponseEntity<>(newAccount, HttpStatus.CREATED);
    }

    // GET /api/v1/accounts/{id}
    @GetMapping
    public ResponseEntity<Account> getAccount(@PathVariable Long id) {
        return ResponseEntity.ok(accountService.getAccount(id));
    }

    // POST /api/v1/transfer
    @PostMapping("/transfer")
    public ResponseEntity<String> trasnfer(@Valid @RequestBody TransferRequest request) {
        accountService.transferFunds(
                request.getSourceAccountId(),
                request.getDestinationAccountId(),
                request.getAmount()
        );
        return ResponseEntity.ok("Transfer successful");
    }
}
