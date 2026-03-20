package com.transaction.service.transaction.entity;


import com.transaction.service.account.entity.Account;
import com.transaction.service.operationType.entity.OperationType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Setter
@Getter
public class Transaction
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "operation_type_id")
    private OperationType operationType;

    private Double amount;

    private LocalDateTime eventDate;

}
