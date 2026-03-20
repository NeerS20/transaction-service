package com.transaction.service.transaction.controller;

import com.transaction.service.transaction.dto.TransactionRequest;
import com.transaction.service.transaction.dto.TransactionResponse;
import com.transaction.service.transaction.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransactionControllerTest {

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private TransactionController transactionController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateTransaction_Success() {
        TransactionRequest request = new TransactionRequest();
        request.setAccountId(1L);
        request.setOperationTypeId(2);
        request.setAmount(150.0);

        TransactionResponse response = new TransactionResponse(10L, 1L, 2, 156.24, LocalDateTime.now());
        response.setTransactionId(10L);
        response.setAccountId(1L);
        response.setOperationTypeId(2);
        response.setAmount(150.0);

        when(transactionService.create(request)).thenReturn(response);

        ResponseEntity<?> result = transactionController.create(request);

        assertNotNull(result);
        assertEquals(201, result.getStatusCode().value());
        assertEquals(response, result.getBody());

        verify(transactionService, times(1)).create(request);
    }
}