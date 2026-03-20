package com.transaction.service.account.controller;


import com.transaction.service.account.dto.AccountRequest;
import com.transaction.service.account.service.AccountService;
import com.transaction.service.common.exception.BadRequestException;
import io.micrometer.common.util.StringUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/accounts")
public class AccountController
{

    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody AccountRequest accountRequest)
    {
        if(StringUtils.isBlank(accountRequest.getDocumentNumber()))
        {
            throw new BadRequestException("DocumentNumber can't be empty or Null");
        }
        return ResponseEntity.status(HttpStatus.CREATED.value())
                .body(accountService.createAccount(accountRequest));
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id)
    {
        return ResponseEntity.status(200).body(accountService.get(id));
    }

}
