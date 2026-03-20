package com.transaction.service.account.service;

import com.transaction.service.account.dto.AccountRequest;
import com.transaction.service.account.dto.AccountResponse;
import com.transaction.service.account.entity.Account;
import com.transaction.service.account.repository.AccountRepository;
import com.transaction.service.common.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testCreateAccount_Success() {
        AccountRequest request = new AccountRequest();
        request.setDocumentNumber("12345678900");

        Account savedAccount = new Account();
        savedAccount.setAccountId(1L);
        savedAccount.setDocumentNumber("12345678900");

        when(accountRepository.save(any(Account.class))).thenReturn(savedAccount);

        AccountResponse response = accountService.createAccount(request);

        assertNotNull(response);
        assertEquals(1L, response.getAccountId());
        assertEquals("12345678900", response.getDocumentNumber());
        verify(accountRepository, times(1)).save(any(Account.class));
    }


    @Test
    void testGetAccount_Success() {
        Long accountId = 1L;

        Account account = new Account();
        account.setAccountId(accountId);
        account.setDocumentNumber("12345678900");

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));

        AccountResponse response = accountService.get(accountId);

        assertNotNull(response);
        assertEquals(accountId, response.getAccountId());
        assertEquals("12345678900", response.getDocumentNumber());
        verify(accountRepository, times(1)).findById(accountId);
    }

    @Test
    void testGetAccount_NotFound() {
        Long accountId = 1L;

        when(accountRepository.findById(accountId)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            accountService.get(accountId);
        });

        assertEquals("Account Not Found!!", exception.getMessage());
        verify(accountRepository, times(1)).findById(accountId);
    }
}