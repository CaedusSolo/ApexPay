package com.apexpay.wallet.service;

import com.apexpay.wallet.dto.TransferRequest;
import com.apexpay.wallet.exception.AccountNotFoundException;
import com.apexpay.wallet.exception.InsufficientBalanceException;
import com.apexpay.wallet.model.Account;
import com.apexpay.wallet.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TransferService {
    private final AccountRepository accountRepository;

    @Transactional
    public void executeTransfer(TransferRequest request) {
        Account source = accountRepository.findById(request.getSourceAccountId()).orElseThrow(() -> new AccountNotFoundException("Source account not found: " + request.getSourceAccountId()));
        Account destination = accountRepository.findById(request.getDestinationAccountId()).orElseThrow(() -> new AccountNotFoundException("Destination account not found: " + request.getDestinationAccountId()));

        if (source.getBalance().compareTo(request.getAmount()) < 0) {
                throw new InsufficientBalanceException(("Account " + source.getId() + " has insufficient balance."));
        }

        source.setBalance(source.getBalance().subtract(request.getAmount()));
        destination.setBalance(destination.getBalance().add(request.getAmount()));

        accountRepository.save(source);
        accountRepository.save(destination);
    }
}
