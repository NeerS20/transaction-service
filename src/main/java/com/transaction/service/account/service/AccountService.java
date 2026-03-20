package com.transaction.service.account.service;

import com.transaction.service.account.dto.AccountRequest;
import com.transaction.service.account.dto.AccountResponse;
import com.transaction.service.account.entity.Account;
import com.transaction.service.account.repository.AccountRepository;
import com.transaction.service.common.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService
{
    private final AccountRepository accountRepository;

    public AccountResponse createAccount(AccountRequest accountRequest)
    {
        Account account = new Account();
        account.setDocumentNumber(accountRequest.getDocumentNumber());
        Account savedAccount = accountRepository.save(account);
        return new AccountResponse(savedAccount.getAccountId(), savedAccount.getDocumentNumber());
    }

    public AccountResponse get(Long id)
    {
        Account account = accountRepository.findById(id)
                          .orElseThrow(()-> new NotFoundException("Account Not Found!!"));
        return new AccountResponse(account.getAccountId(), account.getDocumentNumber());
    }
}
