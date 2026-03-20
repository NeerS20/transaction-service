package com.transaction.service.transaction.service;


import com.transaction.service.account.entity.Account;
import com.transaction.service.account.repository.AccountRepository;
import com.transaction.service.common.enums.SignType;
import com.transaction.service.common.exception.NotFoundException;
import com.transaction.service.operationType.entity.OperationType;
import com.transaction.service.operationType.repository.OperationTypeRepository;
import com.transaction.service.transaction.dto.TransactionRequest;
import com.transaction.service.transaction.dto.TransactionResponse;
import com.transaction.service.transaction.entity.Transaction;
import com.transaction.service.transaction.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private OperationTypeRepository operationTypeRepository;

    @InjectMocks
    private TransactionService transactionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateTransaction_Success_Credit() {
        TransactionRequest request = new TransactionRequest();
        request.setAccountId(1L);
        request.setOperationTypeId(1);
        request.setAmount(100.0);

        Account account = new Account();
        account.setAccountId(1L);

        OperationType operationType = new OperationType();
        operationType.setId(1);
        operationType.setSignType(SignType.CREDIT);

        Transaction savedTransaction = new Transaction();
        savedTransaction.setTransactionId(10L);
        savedTransaction.setAccount(account);
        savedTransaction.setOperationType(operationType);
        savedTransaction.setAmount(100.0);
        savedTransaction.setEventDate(LocalDateTime.now());

        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        when(operationTypeRepository.findById(1)).thenReturn(Optional.of(operationType));
        when(transactionRepository.save(any(Transaction.class))).thenReturn(savedTransaction);

        TransactionResponse response = transactionService.create(request);

        assertNotNull(response);
        assertEquals(10L, response.getTransactionId());
        assertEquals(1L, response.getAccountId());
        assertEquals(1, response.getOperationTypeId());
        assertEquals(100, response.getAmount());

        verify(accountRepository, times(1)).findById(1L);
        verify(operationTypeRepository, times(1)).findById(1);
        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }

    @Test
    void testCreateTransaction_Success_Debit() {
        TransactionRequest request = new TransactionRequest();
        request.setAccountId(1L);
        request.setOperationTypeId(2);
        request.setAmount(100.0);

        Account account = new Account();
        account.setAccountId(1L);

        OperationType operationType = new OperationType();
        operationType.setId(2);
        operationType.setSignType(SignType.DEBIT);

        Transaction savedTransaction = new Transaction();
        savedTransaction.setTransactionId(11L);
        savedTransaction.setAccount(account);
        savedTransaction.setOperationType(operationType);
        savedTransaction.setAmount(-100.0);
        savedTransaction.setEventDate(LocalDateTime.now());

        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        when(operationTypeRepository.findById(2)).thenReturn(Optional.of(operationType));
        when(transactionRepository.save(any(Transaction.class))).thenReturn(savedTransaction);

        TransactionResponse response = transactionService.create(request);

        assertNotNull(response);
        assertEquals(-100, response.getAmount());

        verify(accountRepository, times(1)).findById(1L);
        verify(operationTypeRepository, times(1)).findById(2);
        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }

    @Test
    void testCreateTransaction_AccountNotFound() {
        TransactionRequest request = new TransactionRequest();
        request.setAccountId(1L);
        request.setOperationTypeId(1);
        request.setAmount(100.1);

        when(accountRepository.findById(1L)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            transactionService.create(request);
        });

        assertEquals("Account not found", exception.getMessage());
        verify(accountRepository, times(1)).findById(1L);
        verify(operationTypeRepository, never()).findById(any());
        verify(transactionRepository, never()).save(any());
    }

    @Test
    void testCreateTransaction_OperationTypeNotFound() {
        TransactionRequest request = new TransactionRequest();
        request.setAccountId(1L);
        request.setOperationTypeId(1);
        request.setAmount(100.5);

        Account account = new Account();
        account.setAccountId(1L);

        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        when(operationTypeRepository.findById(1)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            transactionService.create(request);
        });

        assertEquals("Invalid operation", exception.getMessage());
        verify(accountRepository, times(1)).findById(1L);
        verify(operationTypeRepository, times(1)).findById(1);
        verify(transactionRepository, never()).save(any());
    }
}