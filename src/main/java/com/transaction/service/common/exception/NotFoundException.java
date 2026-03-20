package com.transaction.service.common.exception;

public class NotFoundException extends RuntimeException
{
    public NotFoundException(String msg)
    {
        super(msg);
    }
}