package com.transaction.service.operationType.entity;


import com.transaction.service.common.enums.SignType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "operation_types")
@Getter
@Setter
public class OperationType
{
    @Id
    private Integer id;

    private String description;

    @Enumerated(EnumType.STRING)
    private SignType signType;

}
