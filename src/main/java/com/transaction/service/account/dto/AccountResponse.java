package com.transaction.service.account.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccountResponse
{
    private Long accountId;
    private String documentNumber;
}