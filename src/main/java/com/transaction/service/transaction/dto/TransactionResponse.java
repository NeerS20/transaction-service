package com.transaction.service.transaction.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class TransactionResponse
{
    private Long transactionId;
    private Long accountId;
    private Integer operationTypeId;
    private Double amount;
    private LocalDateTime eventDate;
}
