package com.apexpay.wallet.service;
import com.apexpay.wallet.model.Account;
import com.apexpay.wallet.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;

@Service
@RequiredArgsConstructor // Lombok auto generates a constructor for final fields
public class AccountService {
    private final AccountRepository accountRepository;

    public Account createAccount(BigDecimal initialBalance, String currency) {
        Account account = new Account();
        account.setBalance(initialBalance);
        account.setCurrency(currency);

        return accountRepository.save(account);
    }

    public Account getAccount(Long id) {
       return accountRepository.findById(id) .orElseThrow(() -> new RuntimeException("Account not found"));
    }

    @Transactional
    public void transferFunds(Long sourceId, Long destId, BigDecimal amount) {
        Account source = getAccount(sourceId);
        Account destination = getAccount(destId);

        if (source.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient balance");
        }

        source.setBalance(source.getBalance().subtract(amount));
        destination.setBalance(source.getBalance().add(amount));

        accountRepository.save(source);
        accountRepository.save(destination);
    }
}
