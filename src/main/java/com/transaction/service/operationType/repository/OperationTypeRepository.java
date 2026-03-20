package com.transaction.service.operationType.repository;

import com.transaction.service.operationType.entity.OperationType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationTypeRepository extends JpaRepository<OperationType, Integer>
{
}
