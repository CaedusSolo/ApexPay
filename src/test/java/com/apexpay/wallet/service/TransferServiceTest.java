package com.apexpay.wallet.service;

import com.apexpay.wallet.dto.TransferRequest;
import com.apexpay.wallet.exception.InsufficientBalanceException;
import com.apexpay.wallet.model.Account;
import com.apexpay.wallet.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransferServiceTest {
    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private TransferService transferService;

    @Test
    void shouldThrowExceptionWhenInsufficientFunds() {
        // set up the scenario
        Account source = new Account(1L,  new BigDecimal("50.00"), "MYR");
        Account destination = new Account(2L, new BigDecimal("100.00"), "MYR");

        TransferRequest request = new TransferRequest();
        request.setSourceAccountId(1L);
        request.setDestinationAccountId(2L);
        request.setAmount(new BigDecimal("500.00"));

        when(accountRepository.findById(1L)).thenReturn(Optional.of(source));
        when(accountRepository.findById(2L)).thenReturn(Optional.of(destination));

        // act and assert (execute and verify that it fails)
        assertThrows(InsufficientBalanceException.class, () -> {
            transferService.executeTransfer((request));
        });

        verify(accountRepository, never()).save(any(Account.class));

        assertEquals(new BigDecimal("50.00"), source.getBalance());
    }
}
