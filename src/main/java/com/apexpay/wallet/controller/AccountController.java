package com.apexpay.wallet.controller;
import com.apexpay.wallet.dto.TransferRequest;
import com.apexpay.wallet.model.Account;
import com.apexpay.wallet.service.AccountService;
import com.apexpay.wallet.service.TransferService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;
    private final TransferService transferService;

    // POST /api/v1/accounts
    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestParam BigDecimal initialBalance, @RequestParam String currency) {
        Account newAccount =accountService.createAccount(initialBalance, currency);
        return new ResponseEntity<>(newAccount, HttpStatus.CREATED);
    }

    // GET /api/v1/accounts/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccount(@PathVariable Long id) {
        return ResponseEntity.ok(accountService.getAccount(id));
    }

    // POST /api/v1/accounts/transfer
    @PostMapping("/transfer")
    public ResponseEntity<Map<String, String>> transfer(@Valid @RequestBody TransferRequest request) {
        transferService.executeTransfer(request);
        return ResponseEntity.ok(Map.of("status", "SUCCESS", "message", "Transferred " + request.getAmount() + " successfully."));
    }
}
