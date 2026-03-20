package com.transaction.service.account.controller;

import com.transaction.service.account.dto.AccountRequest;
import com.transaction.service.account.dto.AccountResponse;
import com.transaction.service.account.service.AccountService;
import com.transaction.service.common.exception.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccountControllerTest {

    @Mock
    private AccountService accountService;

    @InjectMocks
    private AccountController accountController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateAccount_Success() {
        AccountRequest request = new AccountRequest();
        request.setDocumentNumber("12345678900");

        AccountResponse response = new AccountResponse(1L, "12345678900");

        when(accountService.createAccount(request)).thenReturn(response);

        ResponseEntity<?> result = accountController.create(request);

        assertEquals(HttpStatus.CREATED.value(), result.getStatusCode().value());
        assertEquals(response, result.getBody());
        verify(accountService, times(1)).createAccount(request);
    }

    @Test
    void testCreateAccount_BlankDocumentNumber() {
        AccountRequest request = new AccountRequest();
        request.setDocumentNumber(" ");

        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            accountController.create(request);
        });

        assertEquals("DocumentNumber can't be empty or Null", exception.getMessage());
        verify(accountService, never()).createAccount(any());
    }


    @Test
    void testGetAccount_Success() {
        Long accountId = 1L;

        AccountResponse response = new AccountResponse(accountId,"12345678900");

        when(accountService.get(accountId)).thenReturn(response);

        ResponseEntity<?> result = accountController.get(accountId);

        assertEquals(200, result.getStatusCode().value());
        assertEquals(response, result.getBody());
        verify(accountService, times(1)).get(accountId);
    }

}
