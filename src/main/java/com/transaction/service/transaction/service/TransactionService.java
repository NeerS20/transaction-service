package com.transaction.service.transaction.service;


import com.transaction.service.account.entity.Account;
import com.transaction.service.account.repository.AccountRepository;
import com.transaction.service.common.exception.NotFoundException;
import com.transaction.service.common.enums.SignType;
import com.transaction.service.operationType.entity.OperationType;
import com.transaction.service.operationType.repository.OperationTypeRepository;
import com.transaction.service.transaction.dto.TransactionRequest;
import com.transaction.service.transaction.dto.TransactionResponse;
import com.transaction.service.transaction.entity.Transaction;
import com.transaction.service.transaction.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TransactionService
{

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final OperationTypeRepository operationTypeRepository;

    public TransactionResponse create(TransactionRequest transactionRequest)
    {
        Account acc = accountRepository.findById(transactionRequest.getAccountId())
                .orElseThrow(() -> new NotFoundException("Account not found"));

        OperationType op = operationTypeRepository.findById(transactionRequest.getOperationTypeId())
                .orElseThrow(() -> new NotFoundException("Invalid operation"));

        double finalAmount = op.getSignType() == SignType.CREDIT
                ? Math.abs(transactionRequest.getAmount())
                : -Math.abs(transactionRequest.getAmount());

        Transaction txn = new Transaction();
        txn.setAccount(acc);
        txn.setOperationType(op);
        txn.setAmount(finalAmount);
        txn.setEventDate(LocalDateTime.now());

        Transaction saved = transactionRepository.save(txn);

        return new TransactionResponse(
                saved.getTransactionId(),
                acc.getAccountId(),
                op.getId(),
                saved.getAmount(),
                saved.getEventDate()
        );

    }


}
